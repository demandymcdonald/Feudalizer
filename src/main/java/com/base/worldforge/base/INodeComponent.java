package com.base.worldforge.base;

import com.base.worldforge.lexer.TokenType;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import com.utilities.id.Identifiable;
import org.apache.commons.lang3.tuple.Pair;

public interface INodeComponent extends Identifiable<Long> {
    Table<Long,Long,Long> IDCache = HashBasedTable.create();
    private static Long buildID(long ipid, long pkid){
        Long pre = IDCache.get(ipid, pkid);
        if(pre == null){
            Hasher hasher = Hashing.murmur3_128().newHasher();
            hasher.putLong(ipid);
            hasher.putLong(pkid);
            long hash = hasher.hash().asLong();
            IDCache.put(ipid, pkid, hash);
            return hash;
        }
        return pre;
    }
    TokenType getType();
    long getIPID();
    long getPKID();
    default Long getID(){
        return buildID(this.getIPID(), this.getPKID());
    }
    int getLine();
    int getColumn();
    String getSource(); //May remove if nobody uses it.



    public static long pack(int line, int column){
        int control;
        if(line <0 || column < 0){
            //As a reminder, negative numbers are used to indicate impliedBrackets, etc and link directly to the parent if you to Math.abs().
            control = 1;
            line = Math.abs(line);
            column = Math.abs(column);
        } else {
            control = 0;
        }
        return ((long) control << 63) | ((long) line << 31) | column;
    }
    static Pair<Integer,Integer> unpack(long packed){
        int control = (int) (packed >>> 63);
        int line = (int) ((packed >>> 31) & 0x7FFFFFFFL);
        int column = (int) (packed & 0xFFFFFFFFL);
        if (control == 1){
            line = -line;
            column = -column;
        }
        return Pair.of(line, column);
    }
}
