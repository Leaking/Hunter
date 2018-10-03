package com.quinn.hunter.plugin.debug.bytecode;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public final class Utils implements Opcodes {


    public static int getLoadOpcodeFromDesc(String desc){
        int opcode = ILOAD;
        if("F".equals(desc)) {
            opcode = FLOAD;
        } else if("J".equals(desc)) {
            opcode = LLOAD;
        } else if("D".equals(desc)) {
            opcode = DLOAD;
        } else if(desc.startsWith("L")) {  //object
            opcode = ALOAD;
        } else if(desc.startsWith("[")) {  //array
            opcode = ALOAD;
        }
        return opcode;
    }

    public static int getStoreOpcodeFromType(Type type){
        int opcode = ISTORE;
        switch (type.getSort()) {
            case Type.LONG:
                opcode = LSTORE;
                break;
            case Type.FLOAT:
                opcode = FSTORE;
                break;
            case Type.DOUBLE:
                opcode = DSTORE;
                break;
            case Type.OBJECT:
                opcode = ASTORE;
                break;
            case Type.ARRAY:
                opcode = ASTORE;
                break;
        }
        return opcode;
    }

    public static int getLoadOpcodeFromType(Type type){
        int opcode = ILOAD;
        switch (type.getSort()) {
            case Type.LONG:
                opcode = LLOAD;
                break;
            case Type.FLOAT:
                opcode = FLOAD;
                break;
            case Type.DOUBLE:
                opcode = DLOAD;
                break;
            case Type.OBJECT:
                opcode = ALOAD;
                break;
            case Type.ARRAY:
                opcode = ALOAD;
                break;
        }
        return opcode;
    }
}
