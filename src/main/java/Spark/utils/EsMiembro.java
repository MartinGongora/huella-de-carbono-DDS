package Spark.utils;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;

import java.io.IOException;

public enum EsMiembro  implements Helper<Integer>{

    esMiembro{
        @Override
        public Object apply(Integer context, Options options) throws IOException {
            Options.Buffer buffer = options.buffer();
            if (context == 2) {
                buffer.append(options.fn());
            } else {
                buffer.append(options.inverse());
            }
            return buffer;
        }
    }
}
