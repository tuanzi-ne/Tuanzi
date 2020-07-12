(function ($) {
	"use strict";
	$(function () {
		var el = $('#user_pass');
		var innerToggle = (1 == hideShowPasswordVars.innerToggle) ? true : false;
		var enableTouchSupport = false;
		if ( ('ontouchstart' in window) || window.DocumentTouch && document instanceof DocumentTouch ) {
			enableTouchSupport = true;
		}
		el.hideShowPassword( false, innerToggle, {
			toggle: {
				touchSupport: enableTouchSupport
			}
		});
		if ( false == innerToggle ) {
			var checkbox = $('<label class="hideShowPassword-checkbox"><input type="checkbox" /> '+hideShowPasswordVars.checkboxLabel+'</label>').insertAfter(el.parent('label'));
			checkbox.on( 'change.hideShowPassword', 'input[type="checkbox"]', function() {
				el.togglePassword().focus();
			})
		}
	});
}(jQuery));
