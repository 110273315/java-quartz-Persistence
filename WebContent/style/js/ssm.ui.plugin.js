/*
消息提示组件
*/ + function($) {"use strict";
	var Message = function(container, options) {
		this.container = $(container);
		this.$element = $(Message.DEFAULTS.TEMPLATE);
		this.options = options;
		this.init();
	};
	Message.DEFAULTS = {
		delay : 2000,
		type : 'info'
	};
	Message.prototype.init = function() {
		var self = this;
		$('.alert-dismissable').remove();
		this.content = this.$element.find('[data-toggle="content"]').html(this.options.content);
		switch(this.options.type){
			case 'success':
				this.content.before($('<i class="fa fa-check pr10"></i>'));
				this.$element.addClass('alert-success');
				break;
			case 'info':
				this.content.before($('<i class="fa fa-info pr10"></i>'));
				this.$element.addClass('alert-info');
				break;
			case 'warning':
				this.content.before($('<i class="fa fa-warning pr10"></i>'));
				this.$element.addClass('alert-warning');
				break;
			case 'error':
				this.content.before($('<i class="fa fa-remove pr10"></i>'));
				this.$element.addClass('alert-danger');
				break;
		}
		this.$element.appendTo($('body')).fadeIn(400, function() {
			var width = self.$element.find('[data-toggle="content"]').outerWidth(true) * 0.5 + 20;
			var height = self.$element.find('[data-toggle="content"]').outerHeight(true) * 0.5 + 20;
			var left = self.container.offset().left + self.container.outerWidth(true) * 0.5 - width;
			var outerHeight = self.container.outerHeight(true);
			outerHeight = outerHeight>600 ? outerHeight*0.1 : outerHeight>500? outerHeight*0.7: outerHeight*0.1;
			var top = self.container.offset().top + outerHeight - height *0.5;
			self.$element.css({
				'position' : 'fixed',
				'left' : left + 'px',
				'top' : top + 'px'
			});
		});
		setTimeout(function() {
 			self.$element.fadeOut(1000, function() {
 				 $(this).remove();
 			});
		}, this.options.delay);
	};
	Message.DEFAULTS.TEMPLATE = '<div class="alert alert-dismissable">' + '<button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>' + '<span data-toggle="content" style="font-size:14px; word-wrap:break-word;"></span>&nbsp;&nbsp;</div>';
	var old = $.fn.message;
	$.fn.message = function(option) {
		return this.each(function() {
			var $this = $(this);
			var data = $this.data('koala.message');
			var options = $.extend({}, Message.DEFAULTS, $this.data(), typeof option == 'object' && option);
			$this.data('koala.message', ( data = new Message(this, options)));
			if ( typeof option == 'string') {
				data[option]();
			}
		});
	};
	$.fn.message.Constructor = Message;
	$.fn.message.noConflict = function() {
		$.fn.message = old;
		return this;
	};
}(window.jQuery)