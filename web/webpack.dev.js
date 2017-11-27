var webpack = require("webpack");
var merge = require("webpack-merge");
var path = require("path");

var kotlinPath = path.resolve(__dirname, "build/classes/kotlin/main");
module.exports = merge(require("./webpack.common.js"), {
    devtool: "inline-source-map",
    resolve: {
        modules: [path.resolve(kotlinPath, "dependencies/")]
    },
    devServer: {
        contentBase: "./src/main/web/",
        port: 9000,
        hot: true,
        proxy: [
            {
                context: ["/news", "/feedback", "/notifications/register/web"],
                target: "http://localhost:8080",
                ws: true
            }
        ],
        headers: {
          "Access-Control-Allow-Origin": "*",
          "Access-Control-Allow-Methods": "GET, POST, PUT, DELETE, PATCH, OPTIONS",
          "Access-Control-Allow-Headers": "content-type"
        }
    },
    plugins: [
        new webpack.HotModuleReplacementPlugin()
    ]
});