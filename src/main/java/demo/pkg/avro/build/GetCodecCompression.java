package demo.pkg.avro.build;


import org.apache.avro.file.CodecFactory;

/**
 * Get the codec compression engine for the avro schema's
 *
 * @author rishushrivastava
 */
public class GetCodecCompression {

    public CodecFactory getCodec(String name){

        if (name == null || name.equalsIgnoreCase("null")) {
            return CodecFactory.nullCodec();
        }

        CodecFactory codecFactory;

        switch (name) {
            case "snappy":
                codecFactory = CodecFactory.snappyCodec();
                break;

            case "gzip":
            case "deflate":
                codecFactory = CodecFactory.deflateCodec(CodecFactory.DEFAULT_DEFLATE_LEVEL);
                break;

            case "bzip2":
                codecFactory = CodecFactory.bzip2Codec();
                break;

            default:
                codecFactory = CodecFactory.deflateCodec(CodecFactory.DEFAULT_DEFLATE_LEVEL);
                break;
        }

        return codecFactory;
    }
}
