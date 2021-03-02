// pages/show/show.js

Page({
  GetInfo:function(){
    var that = this;
    wx.request({
      url: 'http://192.168.0.105:8080/test/ShowContest',
      header: { 'content-type': 'application/json' },
      data: null,
      success: function (res) {
        var jsonRes = res.data.split(";");
        var arrayRes = [];
        if(res.data != "")
        {
          for (var i = 0; i <= jsonRes.length - 2; i++) {
            arrayRes.push(JSON.parse(jsonRes[i]));
          }
          that.setData({
            list: arrayRes
          });
        }
        else
        {
          that.setData({
            list:[],
            none: true,
          })
        }

      },
      fail: function (res) {
        that.setData({
          list: [],
          none: true,
        })
      }
    });
  },

  Join: function (event) {
    var id = event.currentTarget.dataset.id;
    var that = this;
    wx.request({
      url: 'http://192.168.0.105:8080/test/JoinContest',
      data: "id="+id,
      method: "POST",
      header:{
        "Content-Type": "application/x-www-form-urlencoded"
      },
      success: function (res) {
        that.GetInfo();
        switch (res.data){
          case 1:
            wx.showToast({
              title: '加入成功', //弹框内容
              icon: 'success',  //弹框模式
              duration: 2000    //弹框显示时间
            });
            break;
          case 2:
            wx.showToast({
              title: '你已加入该比赛', //弹框内容
              icon: 'none',  //弹框模式
              duration: 2000    //弹框显示时间
            });
            break;
          case 3:
            wx.showToast({
              title: '无法加入比赛', //弹框内容
              icon: 'none',  //弹框模式
              duration: 2000    //弹框显示时间
            });
            break;
          default:
            break;
        }
      },
      fail: function(res){
      },
    });
  },
  
  /**
   * 页面的初始数据
   */
  data: {
    list: [],
    none: false,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.GetInfo();
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})