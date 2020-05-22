package ring.central.assessment.common.result;

/**
 * @Description: 客户端的HTTP调用的应答结果类
 * @Author: zuo.li zuo.li@luckincoffee.com
 * @Date: 2020/5/22 15:40
 */
public class ResultInfo {

    /**
     * 应答结果状态码——成功
     */
    public static final int RESULT_CODE_SUCCESS = 0;

    /**
     * 应答结果状态码——通用错误
     */
    public static final int RESULT_CODE_ERROR = 9999;

    /**
     * 返回状态，默认0成功
     */
    private int status = RESULT_CODE_SUCCESS;

    /**
     * 返回状态描述
     */
    private String statusInfo = "SUCCESS";

    /**
     * 返回数据
     */
    private Object data;

    /**
     * 默认构造函数
     */
    public ResultInfo() {}

    /**
     * 带状态和信息的构造函数
     *
     * @param status 状态
     * @param statusInfo 提示信息
     */
    public ResultInfo(int status, String statusInfo) {
        this.status = status;
        this.statusInfo = statusInfo;
    }

    /**
     * 返回一个成功结果
     *
     * @return 成功结果
     */
    public static ResultInfo success() {
        ResultInfo resultInfo = new ResultInfo();
        return resultInfo;
    }

    /**
     * 返回一个带数据的成功结果
     *
     * @param data 数据
     * @return 成功结果
     */
    public static ResultInfo success(Object data) {
        ResultInfo res = new ResultInfo();
        res.setData(data);
        return res;
    }

    /**
     * 返回一个带错误信息的错误结果
     *
     * @param errorMessage 错误信息
     * @return 错误结果
     */
    public static ResultInfo errorMessage(String errorMessage) {
        ResultInfo resultInfo = new ResultInfo(RESULT_CODE_ERROR, errorMessage);
        return resultInfo;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((data == null) ? 0 : data.hashCode());
        result = prime * result + status;
        result = prime * result + ((statusInfo == null) ? 0 : statusInfo.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ResultInfo other = (ResultInfo) obj;
        if (data == null) {
            if (other.data != null) {
                return false;
            }
        } else if (!data.equals(other.data)) {
            return false;
        }
        if (status != other.status) {
            return false;
        }
        if (statusInfo == null) {
            return other.statusInfo == null;
        } else {
            return statusInfo.equals(other.statusInfo);
        }
    }

    public boolean isSuccess() {
        return this.status == 0;
    }

    /**
     * 获取 返回状态，默认0成功
     *
     * @return status 返回状态，默认0成功
     */
    public int getStatus() {
        return this.status;
    }

    /**
     * 设置 返回状态，默认0成功
     *
     * @param status 返回状态，默认0成功
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * 获取 返回状态描述
     *
     * @return statusInfo 返回状态描述
     */
    public String getStatusInfo() {
        return this.statusInfo;
    }

    /**
     * 设置 返回状态描述
     *
     * @param statusInfo 返回状态描述
     */
    public void setStatusInfo(String statusInfo) {
        this.statusInfo = statusInfo;
    }

    /**
     * 获取 返回数据
     *
     * @return data 返回数据
     */
    public Object getData() {
        return this.data;
    }

    /**
     * 设置 返回数据
     *
     * @param data 返回数据
     */
    public void setData(Object data) {
        this.data = data;
    }
}
