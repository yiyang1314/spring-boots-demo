package com.boot.pool.result;

public enum ResultMsg {
	SUCCESS("执行成功！"),
    FAILD("执行失败");//记住要用分号结束

    private String desc;//中文描述

    /**
     * 私有构造,防止被外部调用
     * @param desc
     */
    private ResultMsg(String desc){
        this.desc=desc;
    }

    /**
     * 定义方法,返回描述,跟常规类的定义没区别
     * @return
     */
    public String getDesc(){
        return desc;
    }

    public static void main(String[] args){
        for (ResultMsg  day:ResultMsg.values()) {
            System.out.println("name:"+day.name()+
                    ",desc:"+day.getDesc());
        }
        //----------------------------------------------------------
        System.out.println(ResultMsg.SUCCESS);
        System.out.println(ResultMsg.FAILD);
        ResultMsg m= ResultMsg.SUCCESS;
        System.out.println( m.getDesc());
    }


}
