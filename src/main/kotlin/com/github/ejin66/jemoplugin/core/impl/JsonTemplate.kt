package com.github.ejin66.jemoplugin.core.impl

class JsonTemplate {

    companion object {

        fun template(
            cls: String,
            extendsInfo: String,
            varsInfo: String,
            constructInfo: String,
            fromJson: String,
            toJson: String
        ): String {

            return """
class $cls $extendsInfo{
$varsInfo

    $cls({
$constructInfo
    });

    factory $cls.fromRawJson(String str) => $cls.fromJson(json.decode(str));

    String toRawJson() => json.encode(toJson());

    factory $cls.fromJson(Map<String, dynamic> json) => $cls(
$fromJson
    );

    Map<String, dynamic> toJson() => {
$toJson
    };
}
            """.trim()

        }


    }

}