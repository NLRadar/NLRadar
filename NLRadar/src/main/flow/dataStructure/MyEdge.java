package dataStructure;

import dataStructure.FunctionTaintInfo;

import soot.Unit;

public class MyEdge {
    private FunctionTaintInfo srcFunctionTaintInfo;
    private FunctionTaintInfo dgtFunctionTaintInfo;
    private Unit srcUnit;

    public MyEdge(FunctionTaintInfo srcFunctionTaintInfo, FunctionTaintInfo dgtFunctionTaintInfo, Unit srcUnit) {
        this.srcFunctionTaintInfo = srcFunctionTaintInfo;
        this.dgtFunctionTaintInfo = dgtFunctionTaintInfo;
        this.srcUnit = srcUnit;
    }

    public FunctionTaintInfo getSrcFunctionTaintInfo() {
        return srcFunctionTaintInfo;
    }

    public FunctionTaintInfo getDgtFunctionTaintInfo() {
        return dgtFunctionTaintInfo;
    }

    public Unit getSrcUnit() {
        return srcUnit;
    }
}

