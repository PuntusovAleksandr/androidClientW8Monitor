/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Enum
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 */
package com.lefu.es.constant;

public final class LevelType
extends Enum<LevelType> {
    public static final /* enum */ LevelType AMAEUR;
    private static final /* synthetic */ LevelType[] ENUM$VALUES;
    public static final /* enum */ LevelType ORDINARY;
    public static final /* enum */ LevelType PROFESSIONAL;
    private int id;
    private String value;

    static {
        ORDINARY = new LevelType("ORDINARY", 0, "Ordinary", 2131361863);
        AMAEUR = new LevelType("AMAEUR", 1, "Amaeur", 2131361864);
        PROFESSIONAL = new LevelType("PROFESSIONAL", 2, "Professional", 2131361865);
        LevelType[] arrlevelType = new LevelType[]{ORDINARY, AMAEUR, PROFESSIONAL};
        ENUM$VALUES = arrlevelType;
    }

    private LevelType(String string3, int n2, String string4, int n3) {
        super(string2, n);
        this.id = n2;
        this.value = string3;
    }

    public static LevelType valueOf(String string2) {
        return (LevelType)Enum.valueOf((Class)LevelType.class, (String)string2);
    }

    public static LevelType[] values() {
        LevelType[] arrlevelType = ENUM$VALUES;
        int n = arrlevelType.length;
        LevelType[] arrlevelType2 = new LevelType[n];
        System.arraycopy((Object)arrlevelType, (int)0, (Object)arrlevelType2, (int)0, (int)n);
        return arrlevelType2;
    }

    public int getId() {
        return this.id;
    }

    public String getValue() {
        return this.value;
    }
}

