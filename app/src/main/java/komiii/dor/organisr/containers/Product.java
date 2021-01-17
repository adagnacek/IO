package komiii.dor.organisr.containers;

import java.util.ArrayList;

public class Product {

    int resultId;
    String result;
    int resultType;
    double resultPMin;
    double resultPMax;
    int resultList;
    int resultQuantity;

    public int getResultId() {
        return resultId;
    }

    public void setResultId(int resultId) {
        this.resultId = resultId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getResultType() {
        return resultType;
    }

    public void setResultType(int resultType) {
        this.resultType = resultType;
    }

    public double getResultPMin() {
        return resultPMin;
    }

    public void setResultPMin(double resultPMin) {
        this.resultPMin = resultPMin;
    }

    public double getResultPMax() {
        return resultPMax;
    }

    public void setResultPMax(double resultPMax) {
        this.resultPMax = resultPMax;
    }

    public int getResultList() {
        return resultList;
    }

    public void setResultList(int resultList) {
        this.resultList = resultList;
    }

    public int getResultQuantity() {
        return resultQuantity;
    }

    public void setResultQuantity(int resultQuantity) {
        this.resultQuantity = resultQuantity;
    }
}
