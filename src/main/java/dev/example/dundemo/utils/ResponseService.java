package dev.example.dundemo.utils;

import dev.example.dundemo.utils.model.CommonResult;
import dev.example.dundemo.utils.model.ListResult;
import dev.example.dundemo.utils.model.MapResult;
import dev.example.dundemo.utils.model.SingleResult;
import dev.example.dundemo.utils.response.CommonResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ResponseService {
    public CommonResult getSuccessResult() {
        CommonResult result = new CommonResult();
        setSuccessResult(result);
        return result;
    }

    public <T> SingleResult<T> getSingleResult(T data) {
        SingleResult<T> result = new SingleResult<>();
        result.setData(data);
        setSuccessResult(result);
        return result;
    }

    public <T> ListResult<T> getListResult(List<T> data) {
        ListResult<T> result = new ListResult<>();
        result.setData(data);
        setSuccessResult(result);
        return result;
    }

    public CommonResult getFailResult() {
        CommonResult result = new CommonResult();
        setFailResult(result);
        return result;
    }

    public CommonResult getFailResult(int code, String msg) {
        CommonResult result = new CommonResult();
        setFailResult(result, code, msg);
        return result;
    }

    public <T, K> MapResult getFailResult(int code, String msg, Map<T, K> data) {
        MapResult<T, K> result = new MapResult<>();
        result.setSuccess(false);
        result.setData(data);
        setFailResult(result, code, msg);
        return result;
    }

    private void setSuccessResult(CommonResult result) {
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMessage(CommonResponse.SUCCESS.getMsg());
    }

    private void setFailResult(CommonResult result) {
        result.setSuccess(false);
        result.setCode(CommonResponse.FAIL.getCode());
        result.setMessage(CommonResponse.FAIL.getMsg());
    }

    private void setFailResult(CommonResult result, int code, String msg) {
        result.setSuccess(false);
        result.setCode(code);
        result.setMessage(msg);
    }

}
