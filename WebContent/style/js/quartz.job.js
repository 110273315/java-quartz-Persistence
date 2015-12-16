var Job={
		createTable: function(){
			$('#jobtable').DataTable().destroy();
		    table = $('#jobtable').DataTable({
		      "sDom": 'Rt<"dt-panelfooter clearfix"ip>',
		      "ajax": {
		    	  type:"post",
		    	  url:contextPath +"/job/list.do"
		    	  },
		      "oLanguage": {       
                  "sInfo": "当前数据为从第 _START_ 到第  _END_ 条数据；总共有 _TOTAL_ 条记录",   // 汉化
                  "sLengthMenu": "每页显示  _MENU_ 条记录",     
                  "sZeroRecords": "没有检索到数据",     
                  "sInfoEmpty": "当前数据为从第<font color='red'>0</font>到第<font color='red'> 0</font>条数据;总共有<font color='red'>0</font>条记录",     
                  "sProcessing": "正在加载数据...",     
                  "sSearch":"过滤名称：",  
                  "oPaginate": {     
                      "sFirst": "首页",     
                      "sPrevious": "前页 ",     
                      "sNext": " 后页 ",     
                      "sLast": " 尾页"    
                  }
				},
		      "columns": [
		      { 
		      "width" : "8%",
		      data: "schedule_job_id",
		      "defaultContent" : ''
		      }, { 
	    		  "width" : "8%",
	    		  data: "job_name",
	    		  "defaultContent" : ''
	    	  }, { 
		        "width" : "8%",
		        data: "job_group" ,
		        "defaultContent" : ''
		      },{ 
		        "width" : "15%",
		        data: "job_trigger" ,
		        "defaultContent" : ''
			  },{ 
				 "width" : "10%",
		      	data: "status",
		      	"defaultContent" : ''
		      }, { 
		    	  "width" : "15%",
		    	  data: "cron_expression",
		    	  "defaultContent" : ''
		      }, { 
		    	  "width" : "10%",
		    	  data: "description",
		    	  "defaultContent" : ''
		      }, {
		        "width" : "30%",
		        "defaultContent": '<button type="button" id="refuse" class="btn1  btn-info btn-xs"><span class="glyphicon glyphicon-ok" aria-hidden="true">单次</span></button>'
		        	 + "&nbsp; "+ '<button type="button" id="refuse" class="btn2 btn-danger btn-xs"><span class="glyphicon glyphicon-remove" aria-hidden="true">删除</span></button>'
		        	 + "&nbsp; "+ '<button type="button" id="refuse" class="btn3 btn-info btn-xs"><span class="glyphicon glyphicon-edit" aria-hidden="true">修改</span></button>'
		        	 + "&nbsp; "+ '<button type="button" id="refuse" class="btn4 btn-info btn-xs"><span class="glyphicon glyphicon-off" aria-hidden="true">暂停/恢复</span></button>'
		      }
		      ],
		      "order": [[1, 'asc']]
		    });
		    
		},
		restart:function(){
			$('#jobtable tbody').on('click','td > button.btn4',function(){
			      var tr = $(this).closest('tr');
			      var row = table.row( tr );
			      var object = row.data();
			     
			      if ('NORMAL' == object.status)
			    	  Job.pause(object);
			      else if ('PAUSED' == object.status)
			    	  Job.resume(object);
			      else
		            $("#jobtable").message({
		                  type : "error",
		                  content : "状态异常"
		                });
			});
		},
		pause:function (object){
			$.ajax({
				url:contextPath +"/job/pause.do",
				dataType: "json",
		        data: {
		        	job_id: object.schedule_job_id,
		        	job_name: object.job_name
		          },
		        type: "POST",
		        success: function(data){
		            if (data.success) {
		            	 $("#jobtable").message({
			                  type : "success",
			                  content : "【暂停】执行成功"
			                });
			        
			              } else {
			                $("#jobtable").message({
			                  type : "error",
			                  content : data.errorMessage
			                });
		              }
		            Job.createTable();
		        }
			});
		},
		resume:function (object){
			$.ajax({
				url:contextPath +"/job/resume.do",
				dataType: "json",
				data: {
					job_id: object.schedule_job_id,
					job_name: object.job_name
				},
				type: "POST",
				success: function(data){
					if (data.success) {
						$("#jobtable").message({
							type : "success",
							content : "【恢复】执行成功"
						});
						
					} else {
						$("#jobtable").message({
							type : "error",
							content : data.errorMessage
						});
					}
					Job.createTable();
				}
			});
		},
		//只运行一次
		runOnce : function (){
			$('#jobtable tbody').on('click','td > button.btn1',function(){
			      var tr = $(this).closest('tr');
			      var row = table.row( tr );
			      var object = row.data();
					$.ajax({
						url:contextPath +"/job/runOnce.do",
						dataType: "json",
				        data: {
				        	job_id: object.schedule_job_id,
				        	job_name: object.job_name
				          },
				        type: "POST",
				        success: function(data){
				            if (data.success) {
				            	 $("#jobtable").message({
					                  type : "success",
					                  content : "单次执行成功"
					                });
					        
					              } else {
					                $("#jobtable").message({
					                  type : "error",
					                  content : data.errorMessage
					                });
				              }
				            Job.createTable();
				        }
					});
			 });
		},
		deleteRow : function()
		{
			 //删除
		    $('#jobtable tbody').on('click','td > button.btn2',function(){
		    	var tr = $(this).closest('tr');
				var row = table.row( tr );
				var data1 = row.data();
				var job_id = data1.schedule_job_id;
				$.ajax({
					url:contextPath +"/job/delete.do",
					dataType: "json",
			        data: {
			        	job_id: job_id,
			          },
			        type: "POST",
			        success: function(data){
			            if (data.success) {
			            	 $("#jobtable").message({
				                  type : "success",
				                  content : "删除成功！"
				                });
				        
				              } else {
				                $("#jobtable").message({
				                  type : "error",
				                  content : data.errorMessage
				                });
			              }
			            Job.createTable();
			        }
				});
			  });
		},
		editRow : function(){
			 //审批通过，修改工作地点，弹出Modal页面
		    $('#jobtable tbody').on('click','td > button.btn3',function(){
		    	var tr = $(this).closest('tr');
			    var row = table.row( tr );
			    var rowdata = row.data();
				$.get(contextPath + "/job/input.do").done(
					function(data) {
						Job.initRow(data, rowdata);
					});
		    });
		},
	
		initRow:function(data,rowdata) {
			var dialog = $($.parseHTML(data));
			// 初始化
			if(rowdata)
			{ 
				dialog.find("#schedule_job_id").val(rowdata.schedule_job_id);
				dialog.find("#jobName").val(rowdata.job_name);
				dialog.find("#jobGroup").val(rowdata.job_group);
				dialog.find("#cronExpression").val(rowdata.cron_expression);
				dialog.find("#jobTrigger").val(rowdata.job_trigger);
				dialog.find("#desc").val(rowdata.desc);
			}
			

			//必填项校验，并提交
			dialog.find("#addNewForm").validate({
				rules :{
					jobName:"required",
					jobGroup:"required",
					cronExpression:"required"
					
				},
				messages :{
					jobName:"请输入任务名称！",
					jobGroup:"请输入任务分组",
					cronExpression:"请输入时间表达式",
					jobTrigger:"请输入JOB类路径！"
				},
				submitHandler : function() {
					 Job.submitNewJob(dialog);
				}
			});
			
			dialog.modal().on({
				'hidden.bs.modal' : function() {
					Job.createTable();
					$(".modal-backdrop").remove();
				}
			});
		},
		submitNewJob: function(dialog){
			$.ajax({
				type : 'POST',
				url : contextPath + "/job/submitNewJob.do",
				data:{
					jobId: dialog.find("#schedule_job_id").val(),
					jobName: dialog.find("#jobName").val(),
					jobGroup: dialog.find("#jobGroup").val(),
					cronExpression: dialog.find("#cronExpression").val(),
					jobTrigger: dialog.find("#jobTrigger").val(),
					desc: dialog.find("#desc").val()
				},
				dataType :'json',
				cache: false,
				async: false ,
				success : function(data) {
					dialog.modal('hide');
			           if(data.success) {
			        	   $("#jobtable").message({
	                         type: "success",
	                         content: "操作成功!"
	                       });
	                  }else{
	                   $("#jobtable").message({
	                        type: "error",
	                        content: data.errorMessage
	                      });
	                  }
			     $(".modal-backdrop").remove();
				}
			});
		},
		showRow:function(){
			$('#addJOB').on(
					'click',
					'',
					function() {
						var rowdata = "";
						$.get(contextPath + "/job/input.do").done(
								function(data) {
									Job.initRow(data,rowdata);
								});
					});
		},
		
		init: function(){
			Job.createTable();
			Job.deleteRow();
			Job.editRow();
			Job.showRow();
			Job.runOnce();
			Job.restart();
		}
};
