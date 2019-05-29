package com.diyiliu.support.util;

import lombok.Data;

/**
 * Description: ResponseUtil
 * Author: DIYILIU
 * Update: 2019-05-23 09:40
 *
 * 响应操作结果
 * [0，成功]
 * 错误码：
 * 4xx，前端错误，说明前端开发者需要重新了解后端接口使用规范
 * 401，参数错误，即前端没有传递后端需要的参数
 * 402，参数值错误，即前端传递的参数值不符合后端接收范围
 *
 * 5xx，后端错误，除501外，说明后端开发者应该继续优化代码，尽量避免返回后端错误码
 * 501，验证失败，即后端要求用户登录
 * 502，系统内部错误，即没有合适命名的后端内部错误
 * 503，业务不支持，即后端虽然定义了接口，但是还没有实现功能
 * 504，更新数据失效，即后端采用了乐观锁更新，而并发更新时存在数据更新失效
 * 505，更新数据失败，即后端数据库更新失败（正常情况应该更新成功）。
 */

public class ResponseUtil {

    public static MsgBody ok() {

        return new MsgBody(0).putMsg("成功");
    }

    public static  MsgBody ok(String msg) {

        return new MsgBody(0).putMsg(msg);
    }

    public static <T> MsgBody ok(T t) {

        return new MsgBody(0).putMsg("成功").putData(t);
    }

    public static <T> MsgBody ok(String msg, T t) {

        return new MsgBody(0).putMsg(msg).putData(t);
    }

    public static MsgBody fail() {
        return new MsgBody(-1).putMsg("失败");
    }

    public static MsgBody fail(int status, String msg) {
        return new MsgBody(status).putMsg(msg);
    }

    public static MsgBody badArgs() {
        return fail(401, "参数异常");
    }

    public static MsgBody badValue() {
        return fail(402, "参数值异常");
    }

    public static MsgBody unLogin() {
        return fail(501, "请登录");
    }

    public static MsgBody serious() {
        return fail(502, "系统内部错误");
    }

    public static MsgBody unSupport() {
        return fail(503, "业务不支持");
    }

    public static MsgBody updateFail() {
        return fail(504, "更新数据失败");
    }

    public static MsgBody unAuth() {
        return fail(505, "无操作权限");
    }

    @Data
    static class MsgBody<T> {

        public MsgBody(int status) {
            this.status = status;
        }

        private int status;

        private String msg;

        private T data;

        public MsgBody putMsg(String msg) {
            this.msg = msg;
            return this;
        }

        public MsgBody putData(T data) {
            this.data = data;
            return this;
        }
    }
}
