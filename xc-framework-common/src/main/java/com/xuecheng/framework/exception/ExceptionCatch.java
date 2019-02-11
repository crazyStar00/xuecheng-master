package com.xuecheng.framework.exception;

import com.google.common.collect.ImmutableMap;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class ExceptionCatch {

    public  static ImmutableMap.Builder<Class<? extends Throwable>, ResultCode> EXCEPTION_BUILDER = ImmutableMap.builder();
    public static ImmutableMap<Class<? extends Throwable>, ResultCode> EXCEPTION_MAP;
    static {
        EXCEPTION_BUILDER.put(HttpMessageNotReadableException.class, CommonCode.INVALID_PARAM);

        EXCEPTION_MAP=EXCEPTION_BUILDER.build();
    }

    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public ResponseResult customException(CustomException e){
        log.error(e.getMessage(), e);
        return new ResponseResult(e.getResultCode());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult exception(Exception e) {
        ResultCode resultCode = EXCEPTION_MAP.getOrDefault(e, CommonCode.SERVER_ERROR);
        return new ResponseResult(resultCode);
    }
}
