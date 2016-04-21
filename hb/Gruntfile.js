module.exports= function (grunt) {
    grunt.initConfig({
        /*获取packge.json*/
            pkg:grunt.file.readJSON('package.json'),
        /*uglify插件配置信息*/
        uglify:{
            option:{
                stripBanners:true,
                banner:'/*!<%=pkg.name%>-<%=pkg.version%>.js <%=grunt.template.today("yyy-mm-dd")%>*/\n'
            },
            build:{
                src:"js/*.js",
                dest:'build/<%=pkg.name%>-<%=pkg.version%>.js.min.js'
            }
        },
        jshint:{
            build:['Gruntfile.js','js/*.js'],
            options:{
                jshintrc:'.jshintrc'
            }
        },
        watch:{
            build:{
                file:['js/*.js','css/*.css'],
                task:['jshint','uglify'],
                options:{spawm:false}
            }
        }

    });
    /*加载插件*/
    grunt.loadNpmTasks('grunt-contrib-uglify');
    grunt.loadNpmTasks('grunt-contrib-jshint');
    grunt.loadNpmTasks('grunt-contrib-watch');
    grunt.registerTask('default',['jshint','uglify','watch']);
}