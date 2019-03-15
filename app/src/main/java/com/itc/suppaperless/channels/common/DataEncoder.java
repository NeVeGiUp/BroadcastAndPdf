package com.itc.suppaperless.channels.common;


import com.itc.suppaperless.utils.BytesUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/** 命令通道编码类*/
public class DataEncoder extends MessageToByteEncoder<CommandData> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, CommandData nettyData, ByteBuf byteBuf) throws Exception {
        // 写入消息SmartCar的具体内容
        // 1.写入消息的开头的信息标志固定“ITCL”(string类型)
        byteBuf.writeBytes("ITCL".getBytes());
        byteBuf.writeByte(1);
        byteBuf.writeByte(0);
        // 2.写入消息的类型（char类型）
        byteBuf.writeBytes(BytesUtil.shortToByte(nettyData.getStrType()));
        // 3.写入消息的长度(int 类型)
        byteBuf.writeBytes(BytesUtil.intToByte(nettyData.getiMsgLength()));
        // 4.写入session ID(int 类型)
        byteBuf.writeBytes(BytesUtil.intToByte(0));
        // 5.写入终端id(int 类型)
        byteBuf.writeBytes(BytesUtil.intToByte(nettyData.getiSenderID()));
        byteBuf.writeBytes(BytesUtil.shortToByte((short) 30));
        byteBuf.writeBytes(BytesUtil.shortToByte((short) 0));
        // 3.写入消息的内容(byte[]类型)
        byteBuf.writeBytes(nettyData.getContent());
    }
}