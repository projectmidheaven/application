package org.midheaven.application.network;

import org.midheaven.lang.Check;
import org.midheaven.lang.ValueClass;

@ValueClass
public class HttpStatus {
    
    public static final HttpStatus CONTINUE  = HttpStatus.of(100, "Continue");
    public static final HttpStatus SWITCHING_PROTOCOLS = HttpStatus.of(101, "Switching Protocols");
    public static final HttpStatus PROCESSING = HttpStatus.of(102, "Processing");
    public static final HttpStatus EARLY_HINTS_EXPERIMENTAL = HttpStatus.of(103, "Early Hints Experimental");
    public static final HttpStatus OK = HttpStatus.of(200, "OK");
    public static final HttpStatus CREATED = HttpStatus.of(201, "Created");
    public static final HttpStatus ACCEPTED = HttpStatus.of(202, "Accepted");
    public static final HttpStatus NON_AUTHORITATIVE_INFORMATION = HttpStatus.of(203, "Non-Authoritative Information");
    public static final HttpStatus NO_CONTENT = HttpStatus.of(204, "No Content");
    public static final HttpStatus RESET_CONTENT = HttpStatus.of(205, "Reset Content");
    public static final HttpStatus PARTIAL_CONTENT = HttpStatus.of(206, "Partial Content");
    public static final HttpStatus MULTI_STATUS = HttpStatus.of(207, "Multi-Status");
    public static final HttpStatus ALREADY_REPORTED = HttpStatus.of(208, "Already Reported");
    public static final HttpStatus IM_USED = HttpStatus.of(226, "IM Used");
    public static final HttpStatus MULTIPLE_CHOICES = HttpStatus.of(300, "Multiple Choices");
    public static final HttpStatus MOVED_PERMANENTLY = HttpStatus.of(301, "Moved Permanently");
    public static final HttpStatus FOUND = HttpStatus.of(302, "Found");
    public static final HttpStatus SEE_OTHER = HttpStatus.of(303, "See Other");
    public static final HttpStatus NOT_MODIFIED = HttpStatus.of(304, "Not Modified");
    public static final HttpStatus TEMPORARY_REDIRECT = HttpStatus.of(307, "Temporary redirect");
    public static final HttpStatus PERMANENT_REDIRECT = HttpStatus.of(308, "Permanent redirect");
    public static final HttpStatus BAD_REQUEST = HttpStatus.of(400, "Bad Request");
    public static final HttpStatus UNAUTHORIZED = HttpStatus.of(401, "Unauthorized");
    public static final HttpStatus PAYMENT_REQUIRED = HttpStatus.of(402, "Payment Required");
    public static final HttpStatus FORBIDDEN = HttpStatus.of(403, "Forbidden");
    public static final HttpStatus NOT_FOUND = HttpStatus.of(404, "Not Found");
    public static final HttpStatus METHOD_NOT_ALLOWED = HttpStatus.of(405, "Method Not Allowed");
    public static final HttpStatus NOT_ACCEPTABLE = HttpStatus.of(406, "Not Acceptable");
    public static final HttpStatus PROXY_AUTHENTICATION_REQUIRED = HttpStatus.of(407, "Proxy Authentication Required");
    public static final HttpStatus REQUEST_TIMEOUT = HttpStatus.of(408, "Request Timeout");
    public static final HttpStatus CONFLICT = HttpStatus.of(409, "Conflict");
    public static final HttpStatus GONE = HttpStatus.of(410, "Gone");
    public static final HttpStatus LENGTH_REQUIRED = HttpStatus.of(411, "Length Required");
    public static final HttpStatus PRECONDITION_FAILED = HttpStatus.of(412, "Precondition Failed");
    public static final HttpStatus CONTENT_TOO_LARGE = HttpStatus.of(413, "Content Too Large");
    public static final HttpStatus URI_TOO_LONG = HttpStatus.of(414, "URI Too Long");
    public static final HttpStatus UNSUPPORTED_MEDIA_TYPE = HttpStatus.of(415, "Unsupported Media Type");
    public static final HttpStatus RANGE_NOT_SATISFIABLE = HttpStatus.of(416, "Range Not Satisfiable");
    public static final HttpStatus EXPECTATION_FAILED = HttpStatus.of(417, "Expectation Failed");
    public static final HttpStatus IM_A_TEAPOT = HttpStatus.of(418, "I'm a teapot");
    public static final HttpStatus MISDIRECTED_REQUEST = HttpStatus.of(421, "Misdirected Request");
    public static final HttpStatus UNPROCESSABLE_CONTENT = HttpStatus.of(422, "Unprocessable Content");
    public static final HttpStatus LOCKED = HttpStatus.of(423, "Locked");
    public static final HttpStatus FAILED_DEPENDENCY = HttpStatus.of(424, "Failed Dependency");
    public static final HttpStatus TOO_EARLY = HttpStatus.of(425, "Too Early");
    public static final HttpStatus UPGRADE_REQUIRED = HttpStatus.of(426, "Upgrade Required");
    public static final HttpStatus PRECONDITION_REQUIRED = HttpStatus.of(428, "Precondition Required");
    public static final HttpStatus TOO_MANY_REQUESTS = HttpStatus.of(429, "Too Many Requests");
    public static final HttpStatus REQUEST_HEADER_FIELDS_TOO_LARGE = HttpStatus.of(431, "Request Header Fields Too Large");
    public static final HttpStatus UNAVAILABLE_FOR_LEGAL_REASONS = HttpStatus.of(451, "Unavailable For Legal Reasons");
    public static final HttpStatus INTERNAL_SERVER_ERROR = HttpStatus.of(500, "Internal Server Error");
    public static final HttpStatus NOT_IMPLEMENTED = HttpStatus.of(501, "Not Implemented");
    public static final HttpStatus BAD_GATEWAY = HttpStatus.of(502, "Bad Gateway");
    public static final HttpStatus SERVICE_UNAVAILABLE = HttpStatus.of(503, "Service Unavailable");
    public static final HttpStatus GATEWAY_TIMEOUT = HttpStatus.of(504, "Gateway Timeout");
    public static final HttpStatus HTTP_VERSION_NOT_SUPPORTED = HttpStatus.of(505, "HTTP Version Not Supported");
    public static final HttpStatus VARIANT_ALSO_NEGOTIATES = HttpStatus.of(506, "Variant Also Negotiates");
    public static final HttpStatus INSUFFICIENT_STORAGE = HttpStatus.of(507, "Insufficient Storage");
    public static final HttpStatus LOOP_DETECTED = HttpStatus.of(508, "Loop Detected");
    public static final HttpStatus NOT_EXTENDED = HttpStatus.of(510, "Not Extended");
    public static final HttpStatus NETWORK_AUTHENTICATION_REQUIRED = HttpStatus.of(511, "Network Authentication Required");
    
    public static  HttpStatus of(int code, String reasonPhase){
        Check.argumentIsNotEmpty(reasonPhase, "reasonPhase");
        
        return new HttpStatus(code, Integer.toString(code), reasonPhase);
    }
    
    public static HttpStatus of(String code, String reasonPhase){
        Check.argumentIsNotEmpty(code, "code");
        Check.argumentIsNotEmpty(reasonPhase, "reasonPhase");
        
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < code.length(); i++){
            char c = code.charAt(i);
            if (Character.isDigit(c)){
                builder.append(c);
            } else {
                break;
            }
        }
  
        return new HttpStatus(Integer.parseInt(builder.toString()), code, reasonPhase);
    }
    
    private final int intCode;
    private final String code;
    private final String reasonPhase;
    
    private HttpStatus(int intCode,String code, String reasonPhase){
        this.intCode = intCode;
        this.code = code;
        this.reasonPhase = reasonPhase;
    }
    
    public boolean matches(int code){
        return this.intCode == code;
    }
    
    public int intCode(){
        return intCode;
    }
    
    public String code(){
        return code;
    }
    
    public HttpStatusCategory category(){
        return HttpStatusCategory.from(code);
    }
    
    public String reasonPhase(){
        return reasonPhase;
    }
    
    @Override
    public boolean equals(Object obj) {
        return obj instanceof HttpStatus that
            && that.code.equals(this.code);
    }
    
    @Override
    public int hashCode() {
        return intCode;
    }
    
    @Override
    public String toString () {
        return code + " " + reasonPhase;
    }
}
