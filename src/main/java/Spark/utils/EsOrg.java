package Spark.utils;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;

import java.io.IOException;

public enum EsOrg implements Helper<Integer> {

    esOrg{
        @Override
        public Object apply(Integer context, Options options) throws IOException {
            Options.Buffer buffer = options.buffer();
            if (context == 3) {
                buffer.append(options.fn());
            } else {
                buffer.append(options.inverse());
            }
            return buffer;
        }
    }

}

