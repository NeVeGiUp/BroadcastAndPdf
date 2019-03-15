package com.itc.suppaperless.channels.common;


import com.itc.suppaperless.utils.BytesUtil;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/** 命令通道解码类*/

public class DataDecoder extends ByteToMessageDecoder {

    /**
     * 协议头一共24个字节
     * 协议标记Head，String类型，固定填"ITCL"，占据4个字节.
     * 表示数据的长度Length，int类型，从9到12位，占据4个字节.
     */
    private final static int BASE_LENGTH = 24;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer,
                          List<Object> out) throws Exception {
        // 可读长度必须大于基本长度
        if (buffer.readableBytes() >= BASE_LENGTH) {

            // 记录包头开始的index
            int beginReader;

            while (true) {
                // 获取包头开始的index
                beginReader = buffer.readerIndex();
                // 标记包头开始的index
                buffer.markReaderIndex();
                // 读到了协议的开始标志，结束while循环
                byte[] data = new byte[4];
                buffer.readBytes(data);
                String str = new String(data,"gbk");

                //Log.i("str",str);
                if (str.equals("ITCL")) {
                    break;
                }

                // 未读到包头，略过一个字节
                // 每次略过，一个字节，去读取，包头信息的开始标记
                buffer.resetReaderIndex();
                buffer.readByte();

                // 当略过，一个字节之后，
                // 数据包的长度，又变得不满足
                // 此时，应该结束。等待后面的数据到达
                if (buffer.readableBytes() < BASE_LENGTH) {
                    return;
                }
            }

            // 消息的类型
            buffer.skipBytes(2);
            byte[] type = new byte[2];
            buffer.readBytes(type);

            //消息的长度
            byte[] byte_length = new byte[4];
            buffer.readBytes(byte_length);
            int length = BytesUtil.byteToInt(byte_length);
            // 判断请求数据包数据是否到齐
            if (buffer.readableBytes() < length-12) {
                // 还原读指针
                buffer.readerIndex(beginReader);
                return;
            }
            buffer.readBytes(new byte[4]);
            //终端id
            byte[] byte_iSenderID = new byte[4];
            buffer.readBytes(byte_iSenderID);
            int iSenderID = BytesUtil.byteToInt(byte_length);
            buffer.readBytes(new byte[4]);
            // 读取data数据
            byte[] data = new byte[length-24];
            buffer.readBytes(data);
            CommandData protocol = new CommandData( BytesUtil.byteToShort(type),iSenderID,data.length, data);
            out.add(protocol);

        }
    }


}
