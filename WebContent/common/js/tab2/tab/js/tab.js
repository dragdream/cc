var tab = (function($) {

    var events = {};

    function add(id, name) {

        '<div class="js_menu">\
            <a href="javascript:;" class="tab js_menu_item"></a>\
            <a href="javascript:;" class="close js_menu_close" hidefocus="hidefocus"></a>\
        </div>';

        var $menu = $('<div class="js_menu"></div>'),
            $menuItem = $('<a href="javascript:;" class="js_menu_item tab"></a>'),
            $menuClose = $('<a href="javascript:;" class="close js_menu_close" hidefocus="hidefocus"></a>');

        $menuItem.text(name);
        $menu.attr('data-id', id);

        $('#tabs_container .js_ctnr').append(
            $menu.append($menuItem)
                 .append($menuClose)
        );

        if ($('#tabs_container .js_ctnr').width() > $('#taskbar_center').width()) {
            $('#tabs_container').width($('#taskbar_center').width() - 60);
            $('#tabs_left_scroll,#tabs_right_scroll').show();

            scroll(false, 200);
        } else {
            $('#tabs_left_scroll,#tabs_right_scroll').hide();
            $('#tabs_container').width($('#taskbar_center').width());
        }

        setActive($menu);
    }

    function setActive($menu) {
        if (!$menu.hasClass('selected')) {
            $('#tabs_container').find('.selected').removeClass('selected');
            $menu.addClass('selected');

            fire('tabactive', [$menu.attr('data-id')]);

            return true;
        }
        return false;
    }

    function init() {

        $(document).on('click', '.js_menu_item', function() {
            var $menu = $(this).parent();

            setActive($menu);
        });

        $('#tabs_right_scroll').click(function() {
            scroll(false, 100);
        });

        $('#tabs_left_scroll').click(function() {
            scroll(true, 100);
        });

        $('#tabs_container').on('click', '.js_menu_close', function() {
            var $menu = $(this).closest('.js_menu');

            fire('tabremove', [$menu.attr('data-id')]);

            $menu.remove();

            setActive($('#tabs_container .js_menu:first'));
        });

        on('tabremove', function() {
            scroll(true, 10000);

            if ($('#tabs_container .js_ctnr').width() <= $('#taskbar_center').width()) {
                $('#tabs_left_scroll,#tabs_right_scroll').hide();
                $('#tabs_container').width($('#taskbar_center').width());
            }
        });

    }

    function scroll(isLeft, step) {
        var width = $('#tabs_container .js_ctnr').width();
        var ctnWidth = $('#taskbar_center').width() - 60;
        var $first = $('#tabs_container .js_menu:first');
        var left = Math.abs(parseInt($first.css('marginLeft')));

        var ml = 0;

        if (!isLeft) {
            if (left >= ctnWidth - step - width) {
                ml = ctnWidth - width;
            } else {
                ml = '-=' + step + 'px';
            }
        } else {
            if (left <= step) {
                ml = 0;
            } else {
                ml = '+=' + step + 'px';
            }
        }

        $('#tabs_container .js_ctnr').animate({
            marginLeft: ml
        }, 'fast');
    }

    function on(type, fn) {
        events[type] = events[type] || $.Callbacks();

        events[type].add(fn);
    }

    function fire(type, args) {
        var callbacks = events[type];

        if (callbacks) {
            callbacks.fire(args);
        }
    }



    return {
        add: add,
        on: on,
        init: init
    };
}) (jQuery);