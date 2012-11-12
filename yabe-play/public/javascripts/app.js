/**
 * This is the main client-side JavaScript application file.
 * The Backbone MVC structure lives here.
 *
 * @author aksel@agresvig.com
 */

//Load the application once the DOM is ready, using jQuery.ready:
$(function() {

    //The Category model
    var Category = Backbone.Model.extend({

        //Empty Constructor
        initialize: function() { }

    });

    //The Category collection
    var CategoryList = Backbone.Collection.extend({
        model: Category,
        url: '/categories'
    });

    //The Category view element
    var CategoryView = Backbone.View.extend({
        //The DOM element is a div with class="well sidebar-nav" (because of Twitter Bootstrap)
        tagName: 'div',
        className: 'well sidebar-nav',

        //Cache the template defined in the main html, using Underscore.JS
        template: _.template($('#sidebar-template').html()),

        //Our render-function bootstraps the model JSON data into the template
        render: function() {
            this.$el.html(this.template(this.model.toJSON()));

            return this; //To allow for daisy-chaining calls
        }
    });

    //Create global category list
    var Categories = new CategoryList();

    //This view resembles the the main area of our app
    var YabeApp = Backbone.View.extend({

        //We won't generate a new element here, but rather bind to an existing DOM element
        el : $('#page-container'),

        //Fetch the categories and show the CategoryView
        initialize: function() {
            Categories.fetch();

            var catView = new CategoryView({model: Categories}).render();
            this.$('#sidebar').append(catView.render().el);
        }
    });

    var App = new YabeApp;
});