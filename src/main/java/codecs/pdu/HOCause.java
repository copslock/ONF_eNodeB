/**
 * This class file was automatically generated by jASN1 v1.8.2 (http://www.openmuc.org)
 */

package codecs.pdu;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import codecs.api.CRNTI;
import codecs.api.ECGI;
import codecs.ber.BerByteArrayOutputStream;
import codecs.ber.BerLength;
import codecs.ber.BerTag;
import codecs.api.RXSigReport;
import codecs.ber.types.BerBitString;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HOCause implements Serializable {

    public static final BerTag tag = new BerTag(BerTag.UNIVERSAL_CLASS, BerTag.CONSTRUCTED, 16);
    private static final long serialVersionUID = 1L;
    @JsonIgnore
    public byte[] code = null;
    private CRNTI crnti = null;
    private ECGI ecgiS = null;
    private ECGI ecgiT = null;
    private BerBitString hoCause = null;
    private HoTrigger hoTrigger = null;
    public HOCause() {
    }

    public HOCause(byte[] code) {
        this.code = code;
    }

    public CRNTI getCrnti() {
        return crnti;
    }

    public void setCrnti(CRNTI crnti) {
        this.crnti = crnti;
    }

    public ECGI getEcgiS() {
        return ecgiS;
    }

    public void setEcgiS(ECGI ecgiS) {
        this.ecgiS = ecgiS;
    }

    public ECGI getEcgiT() {
        return ecgiT;
    }

    public void setEcgiT(ECGI ecgiT) {
        this.ecgiT = ecgiT;
    }

    public BerBitString getHoCause() {
        return hoCause;
    }

    public void setHoCause(BerBitString hoCause) {
        this.hoCause = hoCause;
    }

    public HoTrigger getHoTrigger() {
        return hoTrigger;
    }

    public void setHoTrigger(HoTrigger hoTrigger) {
        this.hoTrigger = hoTrigger;
    }

    public int encode(BerByteArrayOutputStream os) throws IOException {
        return encode(os, true);
    }

    public int encode(BerByteArrayOutputStream os, boolean withTag) throws IOException {

        if (code != null) {
            for (int i = code.length - 1; i >= 0; i--) {
                os.write(code[i]);
            }
            if (withTag) {
                return tag.encode(os) + code.length;
            }
            return code.length;
        }

        int codeLength = 0;
        codeLength += hoTrigger.encode(os, false);
        // write tag: CONTEXT_CLASS, CONSTRUCTED, 4
        os.write(0xA4);
        codeLength += 1;

        codeLength += hoCause.encode(os, false);
        // write tag: CONTEXT_CLASS, PRIMITIVE, 3
        os.write(0x83);
        codeLength += 1;

        codeLength += ecgiT.encode(os, false);
        // write tag: CONTEXT_CLASS, CONSTRUCTED, 2
        os.write(0xA2);
        codeLength += 1;

        codeLength += ecgiS.encode(os, false);
        // write tag: CONTEXT_CLASS, CONSTRUCTED, 1
        os.write(0xA1);
        codeLength += 1;

        codeLength += crnti.encode(os, false);
        // write tag: CONTEXT_CLASS, PRIMITIVE, 0
        os.write(0x80);
        codeLength += 1;

        codeLength += BerLength.encodeLength(os, codeLength);

        if (withTag) {
            codeLength += tag.encode(os);
        }

        return codeLength;

    }

    public int decode(InputStream is) throws IOException {
        return decode(is, true);
    }

    public int decode(InputStream is, boolean withTag) throws IOException {
        int codeLength = 0;
        int subCodeLength = 0;
        BerTag berTag = new BerTag();

        if (withTag) {
            codeLength += tag.decodeAndCheck(is);
        }

        BerLength length = new BerLength();
        codeLength += length.decode(is);

        int totalLength = length.val;
        codeLength += totalLength;

        subCodeLength += berTag.decode(is);
        if (berTag.equals(BerTag.CONTEXT_CLASS, BerTag.PRIMITIVE, 0)) {
            crnti = new CRNTI();
            subCodeLength += crnti.decode(is, false);
            subCodeLength += berTag.decode(is);
        } else {
            throw new IOException("Tag does not match the mandatory sequence element tag.");
        }

        if (berTag.equals(BerTag.CONTEXT_CLASS, BerTag.CONSTRUCTED, 1)) {
            ecgiS = new ECGI();
            subCodeLength += ecgiS.decode(is, false);
            subCodeLength += berTag.decode(is);
        } else {
            throw new IOException("Tag does not match the mandatory sequence element tag.");
        }

        if (berTag.equals(BerTag.CONTEXT_CLASS, BerTag.CONSTRUCTED, 2)) {
            ecgiT = new ECGI();
            subCodeLength += ecgiT.decode(is, false);
            subCodeLength += berTag.decode(is);
        } else {
            throw new IOException("Tag does not match the mandatory sequence element tag.");
        }

        if (berTag.equals(BerTag.CONTEXT_CLASS, BerTag.PRIMITIVE, 3)) {
            hoCause = new BerBitString();
            subCodeLength += hoCause.decode(is, false);
            subCodeLength += berTag.decode(is);
        } else {
            throw new IOException("Tag does not match the mandatory sequence element tag.");
        }

        if (berTag.equals(BerTag.CONTEXT_CLASS, BerTag.CONSTRUCTED, 4)) {
            hoTrigger = new HoTrigger();
            subCodeLength += hoTrigger.decode(is, false);
            if (subCodeLength == totalLength) {
                return codeLength;
            }
        }
        throw new IOException("Unexpected end of sequence, length tag: " + totalLength + ", actual sequence length: " + subCodeLength);


    }

    public void encodeAndSave(int encodingSizeGuess) throws IOException {
        BerByteArrayOutputStream os = new BerByteArrayOutputStream(encodingSizeGuess);
        encode(os, false);
        code = os.getArray();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        appendAsString(sb, 0);
        return sb.toString();
    }

    public void appendAsString(StringBuilder sb, int indentLevel) {

        sb.append("{");
        sb.append("\n");
        for (int i = 0; i < indentLevel + 1; i++) {
            sb.append("\t");
        }
        if (crnti != null) {
            sb.append("crnti: ").append(crnti);
        } else {
            sb.append("crnti: <empty-required-field>");
        }

        sb.append(",\n");
        for (int i = 0; i < indentLevel + 1; i++) {
            sb.append("\t");
        }
        if (ecgiS != null) {
            sb.append("ecgiS: ");
            ecgiS.appendAsString(sb, indentLevel + 1);
        } else {
            sb.append("ecgiS: <empty-required-field>");
        }

        sb.append(",\n");
        for (int i = 0; i < indentLevel + 1; i++) {
            sb.append("\t");
        }
        if (ecgiT != null) {
            sb.append("ecgiT: ");
            ecgiT.appendAsString(sb, indentLevel + 1);
        } else {
            sb.append("ecgiT: <empty-required-field>");
        }

        sb.append(",\n");
        for (int i = 0; i < indentLevel + 1; i++) {
            sb.append("\t");
        }
        if (hoCause != null) {
            sb.append("hoCause: ").append(hoCause);
        } else {
            sb.append("hoCause: <empty-required-field>");
        }

        sb.append(",\n");
        for (int i = 0; i < indentLevel + 1; i++) {
            sb.append("\t");
        }
        if (hoTrigger != null) {
            sb.append("hoTrigger: ");
            hoTrigger.appendAsString(sb, indentLevel + 1);
        } else {
            sb.append("hoTrigger: <empty-required-field>");
        }

        sb.append("\n");
        for (int i = 0; i < indentLevel; i++) {
            sb.append("\t");
        }
        sb.append("}");
    }

    public static class HoTrigger implements Serializable {

        public static final BerTag tag = new BerTag(BerTag.UNIVERSAL_CLASS, BerTag.CONSTRUCTED, 16);
        private static final long serialVersionUID = 1L;
        @JsonIgnore
        public byte[] code = null;
        private List<RXSigReport> seqOf = null;

        public HoTrigger() {
            seqOf = new ArrayList<RXSigReport>();
        }

        public HoTrigger(byte[] code) {
            this.code = code;
        }


        @JsonValue
        public List<RXSigReport> getRXSigReport() {
            if (seqOf == null) {
                seqOf = new ArrayList<RXSigReport>();
            }
            return seqOf;
        }

        public int encode(BerByteArrayOutputStream os) throws IOException {
            return encode(os, true);
        }

        public int encode(BerByteArrayOutputStream os, boolean withTag) throws IOException {

            if (code != null) {
                for (int i = code.length - 1; i >= 0; i--) {
                    os.write(code[i]);
                }
                if (withTag) {
                    return tag.encode(os) + code.length;
                }
                return code.length;
            }

            int codeLength = 0;
            for (int i = (seqOf.size() - 1); i >= 0; i--) {
                codeLength += seqOf.get(i).encode(os, true);
            }

            codeLength += BerLength.encodeLength(os, codeLength);

            if (withTag) {
                codeLength += tag.encode(os);
            }

            return codeLength;
        }

        public int decode(InputStream is) throws IOException {
            return decode(is, true);
        }

        public int decode(InputStream is, boolean withTag) throws IOException {
            int codeLength = 0;
            int subCodeLength = 0;
            if (withTag) {
                codeLength += tag.decodeAndCheck(is);
            }

            BerLength length = new BerLength();
            codeLength += length.decode(is);
            int totalLength = length.val;

            while (subCodeLength < totalLength) {
                RXSigReport element = new RXSigReport();
                subCodeLength += element.decode(is, true);
                seqOf.add(element);
            }
            if (subCodeLength != totalLength) {
                throw new IOException("Decoded SequenceOf or SetOf has wrong length. Expected " + totalLength + " but has " + subCodeLength);

            }
            codeLength += subCodeLength;

            return codeLength;
        }

        public void encodeAndSave(int encodingSizeGuess) throws IOException {
            BerByteArrayOutputStream os = new BerByteArrayOutputStream(encodingSizeGuess);
            encode(os, false);
            code = os.getArray();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            appendAsString(sb, 0);
            return sb.toString();
        }

        public void appendAsString(StringBuilder sb, int indentLevel) {

            sb.append("{\n");
            for (int i = 0; i < indentLevel + 1; i++) {
                sb.append("\t");
            }
            if (seqOf == null) {
                sb.append("null");
            } else {
                Iterator<RXSigReport> it = seqOf.iterator();
                if (it.hasNext()) {
                    it.next().appendAsString(sb, indentLevel + 1);
                    while (it.hasNext()) {
                        sb.append(",\n");
                        for (int i = 0; i < indentLevel + 1; i++) {
                            sb.append("\t");
                        }
                        it.next().appendAsString(sb, indentLevel + 1);
                    }
                }
            }

            sb.append("\n");
            for (int i = 0; i < indentLevel; i++) {
                sb.append("\t");
            }
            sb.append("}");
        }

    }

}

