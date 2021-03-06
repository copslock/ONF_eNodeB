/**
 * This class file was automatically generated by jASN1 v1.8.0 (http://www.openmuc.org)
 */

package codecs.pdu;



import codecs.ber.BerByteArrayOutputStream;
import codecs.ber.BerLength;
import codecs.ber.BerTag;
import codecs.ber.types.BerBoolean;
import codecs.ber.types.BerInteger;
import codecs.api.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CellConfigReport implements Serializable {

	private static final long serialVersionUID = 1L;

	public static class CandScells implements Serializable {

		private static final long serialVersionUID = 1L;

		public static final BerTag tag = new BerTag(BerTag.UNIVERSAL_CLASS, BerTag.CONSTRUCTED, 16);

		public byte[] code = null;
		private List<CandScell> seqOf = null;

		public CandScells() {
			seqOf = new ArrayList<CandScell>();
		}

		public CandScells(byte[] code) {
			this.code = code;
		}


		public List<CandScell> getCandScells() {
			if (seqOf == null) {
				seqOf = new ArrayList<CandScell>();
			}
			return seqOf;
		}

		public void setCandScells(CandScell candScell) {
			seqOf.add(candScell);
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
				CandScell element = new CandScell();
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

			sb.append("[\n");
			for (int i = 0; i < indentLevel + 1; i++) {
				sb.append("\t");
			}
			if (seqOf == null) {
//				sb.append("null");
			}
			else {
				Iterator<CandScell> it = seqOf.iterator();
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
			sb.append("]");
		}

	}

	public static final BerTag tag = new BerTag(BerTag.UNIVERSAL_CLASS, BerTag.CONSTRUCTED, 16);

	 public byte[] code = null;
	private ECGI ecgi = null;
	private PhysCellId pci = null;
	private CandScells candScells = null;
	private ARFCNValue earfcnDl = null;
	private ARFCNValue earfcnUl = null;
	private BerInteger rbsPerTtiDl = null;
	private BerInteger rbsPerTtiUl = null;
	private BerInteger numTxAntenna = null;
	private DuplexMode duplexMode = null;
	private BerInteger tddConfig = null;
	private BerInteger tddSplSfConfig = null;
	private BerInteger maxNumConnectedUes = null;
	private BerInteger maxNumConnectedBearers = null;
	private BerInteger maxNumUesSchedPerTtiDl = null;
	private BerInteger maxNumUesSchedPerTtiUl = null;
	private BerBoolean dlfsSchedEnable = null;
	
	public CellConfigReport() {
	}

	public CellConfigReport(byte[] code) {
		this.code = code;
	}

	public void setEcgi(ECGI ecgi) {
		this.ecgi = ecgi;
	}

	public ECGI getEcgi() {
		return ecgi;
	}

	public void setPci(PhysCellId pci) {
		this.pci = pci;
	}

	public PhysCellId getPci() {
		return pci;
	}

	public void setCandScells(CandScells candScells) {
		this.candScells = candScells;
	}

	public CandScells getCandScells() {
		return candScells;
	}

	public void setEarfcnDl(ARFCNValue earfcnDl) {
		this.earfcnDl = earfcnDl;
	}

	public ARFCNValue getEarfcnDl() {
		return earfcnDl;
	}

	public void setEarfcnUl(ARFCNValue earfcnUl) {
		this.earfcnUl = earfcnUl;
	}

	public ARFCNValue getEarfcnUl() {
		return earfcnUl;
	}

	public void setRbsPerTtiDl(BerInteger rbsPerTtiDl) {
		this.rbsPerTtiDl = rbsPerTtiDl;
	}

	public BerInteger getRbsPerTtiDl() {
		return rbsPerTtiDl;
	}

	public void setRbsPerTtiUl(BerInteger rbsPerTtiUl) {
		this.rbsPerTtiUl = rbsPerTtiUl;
	}

	public BerInteger getRbsPerTtiUl() {
		return rbsPerTtiUl;
	}

	public void setNumTxAntenna(BerInteger numTxAntenna) {
		this.numTxAntenna = numTxAntenna;
	}

	public BerInteger getNumTxAntenna() {
		return numTxAntenna;
	}

	public void setDuplexMode(DuplexMode duplexMode) {
		this.duplexMode = duplexMode;
	}

	public DuplexMode getDuplexMode() {
		return duplexMode;
	}

	public void setTddConfig(BerInteger tddConfig) {
		this.tddConfig = tddConfig;
	}

	public BerInteger getTddConfig() {
		return tddConfig;
	}

	public void setTddSplSfConfig(BerInteger tddSplSfConfig) {
		this.tddSplSfConfig = tddSplSfConfig;
	}

	public BerInteger getTddSplSfConfig() {
		return tddSplSfConfig;
	}

	public void setMaxNumConnectedUes(BerInteger maxNumConnectedUes) {
		this.maxNumConnectedUes = maxNumConnectedUes;
	}

	public BerInteger getMaxNumConnectedUes() {
		return maxNumConnectedUes;
	}

	public void setMaxNumConnectedBearers(BerInteger maxNumConnectedBearers) {
		this.maxNumConnectedBearers = maxNumConnectedBearers;
	}

	public BerInteger getMaxNumConnectedBearers() {
		return maxNumConnectedBearers;
	}

	public void setMaxNumUesSchedPerTtiDl(BerInteger maxNumUesSchedPerTtiDl) {
		this.maxNumUesSchedPerTtiDl = maxNumUesSchedPerTtiDl;
	}

	public BerInteger getMaxNumUesSchedPerTtiDl() {
		return maxNumUesSchedPerTtiDl;
	}

	public void setMaxNumUesSchedPerTtiUl(BerInteger maxNumUesSchedPerTtiUl) {
		this.maxNumUesSchedPerTtiUl = maxNumUesSchedPerTtiUl;
	}

	public BerInteger getMaxNumUesSchedPerTtiUl() {
		return maxNumUesSchedPerTtiUl;
	}

	public void setDlfsSchedEnable(BerBoolean dlfsSchedEnable) {
		this.dlfsSchedEnable = dlfsSchedEnable;
	}

	public BerBoolean getDlfsSchedEnable() {
		return dlfsSchedEnable;
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
		codeLength += dlfsSchedEnable.encode(os, false);
		// write tag: CONTEXT_CLASS, PRIMITIVE, 15
		os.write(0x8F);
		codeLength += 1;
		
		codeLength += maxNumUesSchedPerTtiUl.encode(os, false);
		// write tag: CONTEXT_CLASS, PRIMITIVE, 14
		os.write(0x8E);
		codeLength += 1;
		
		codeLength += maxNumUesSchedPerTtiDl.encode(os, false);
		// write tag: CONTEXT_CLASS, PRIMITIVE, 13
		os.write(0x8D);
		codeLength += 1;
		
		codeLength += maxNumConnectedBearers.encode(os, false);
		// write tag: CONTEXT_CLASS, PRIMITIVE, 12
		os.write(0x8C);
		codeLength += 1;
		
		codeLength += maxNumConnectedUes.encode(os, false);
		// write tag: CONTEXT_CLASS, PRIMITIVE, 11
		os.write(0x8B);
		codeLength += 1;
		
		if (tddSplSfConfig != null) {
			codeLength += tddSplSfConfig.encode(os, false);
			// write tag: CONTEXT_CLASS, PRIMITIVE, 10
			os.write(0x8A);
			codeLength += 1;
		}
		
		if (tddConfig != null) {
			codeLength += tddConfig.encode(os, false);
			// write tag: CONTEXT_CLASS, PRIMITIVE, 9
			os.write(0x89);
			codeLength += 1;
		}
		
		codeLength += duplexMode.encode(os, false);
		// write tag: CONTEXT_CLASS, PRIMITIVE, 8
		os.write(0x88);
		codeLength += 1;
		
		codeLength += numTxAntenna.encode(os, false);
		// write tag: CONTEXT_CLASS, PRIMITIVE, 7
		os.write(0x87);
		codeLength += 1;
		
		codeLength += rbsPerTtiUl.encode(os, false);
		// write tag: CONTEXT_CLASS, PRIMITIVE, 6
		os.write(0x86);
		codeLength += 1;
		
		codeLength += rbsPerTtiDl.encode(os, false);
		// write tag: CONTEXT_CLASS, PRIMITIVE, 5
		os.write(0x85);
		codeLength += 1;
		
		codeLength += earfcnUl.encode(os, false);
		// write tag: CONTEXT_CLASS, PRIMITIVE, 4
		os.write(0x84);
		codeLength += 1;
		
		codeLength += earfcnDl.encode(os, false);
		// write tag: CONTEXT_CLASS, PRIMITIVE, 3
		os.write(0x83);
		codeLength += 1;
		
		codeLength += candScells.encode(os, false);
		// write tag: CONTEXT_CLASS, CONSTRUCTED, 2
		os.write(0xA2);
		codeLength += 1;
		
		codeLength += pci.encode(os, false);
		// write tag: CONTEXT_CLASS, PRIMITIVE, 1
		os.write(0x81);
		codeLength += 1;
		
		codeLength += ecgi.encode(os, false);
		// write tag: CONTEXT_CLASS, CONSTRUCTED, 0
		os.write(0xA0);
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
		if (berTag.equals(BerTag.CONTEXT_CLASS, BerTag.CONSTRUCTED, 0)) {
			ecgi = new ECGI();
			subCodeLength += ecgi.decode(is, false);
			subCodeLength += berTag.decode(is);
		}
		else {
			throw new IOException("Tag does not match the mandatory sequence element tag.");
		}
		
		if (berTag.equals(BerTag.CONTEXT_CLASS, BerTag.PRIMITIVE, 1)) {
			pci = new PhysCellId();
			subCodeLength += pci.decode(is, false);
			subCodeLength += berTag.decode(is);
		}
		else {
			throw new IOException("Tag does not match the mandatory sequence element tag.");
		}
		
		if (berTag.equals(BerTag.CONTEXT_CLASS, BerTag.CONSTRUCTED, 2)) {
			candScells = new CandScells();
			subCodeLength += candScells.decode(is, false);
			subCodeLength += berTag.decode(is);
		}
		else {
			throw new IOException("Tag does not match the mandatory sequence element tag.");
		}
		
		if (berTag.equals(BerTag.CONTEXT_CLASS, BerTag.PRIMITIVE, 3)) {
			earfcnDl = new ARFCNValue();
			subCodeLength += earfcnDl.decode(is, false);
			subCodeLength += berTag.decode(is);
		}
		else {
			throw new IOException("Tag does not match the mandatory sequence element tag.");
		}
		
		if (berTag.equals(BerTag.CONTEXT_CLASS, BerTag.PRIMITIVE, 4)) {
			earfcnUl = new ARFCNValue();
			subCodeLength += earfcnUl.decode(is, false);
			subCodeLength += berTag.decode(is);
		}
		else {
			throw new IOException("Tag does not match the mandatory sequence element tag.");
		}
		
		if (berTag.equals(BerTag.CONTEXT_CLASS, BerTag.PRIMITIVE, 5)) {
			rbsPerTtiDl = new BerInteger();
			subCodeLength += rbsPerTtiDl.decode(is, false);
			subCodeLength += berTag.decode(is);
		}
		else {
			throw new IOException("Tag does not match the mandatory sequence element tag.");
		}
		
		if (berTag.equals(BerTag.CONTEXT_CLASS, BerTag.PRIMITIVE, 6)) {
			rbsPerTtiUl = new BerInteger();
			subCodeLength += rbsPerTtiUl.decode(is, false);
			subCodeLength += berTag.decode(is);
		}
		else {
			throw new IOException("Tag does not match the mandatory sequence element tag.");
		}
		
		if (berTag.equals(BerTag.CONTEXT_CLASS, BerTag.PRIMITIVE, 7)) {
			numTxAntenna = new BerInteger();
			subCodeLength += numTxAntenna.decode(is, false);
			subCodeLength += berTag.decode(is);
		}
		else {
			throw new IOException("Tag does not match the mandatory sequence element tag.");
		}
		
		if (berTag.equals(BerTag.CONTEXT_CLASS, BerTag.PRIMITIVE, 8)) {
			duplexMode = new DuplexMode();
			subCodeLength += duplexMode.decode(is, false);
			subCodeLength += berTag.decode(is);
		}
		else {
			throw new IOException("Tag does not match the mandatory sequence element tag.");
		}
		
		if (berTag.equals(BerTag.CONTEXT_CLASS, BerTag.PRIMITIVE, 9)) {
			tddConfig = new BerInteger();
			subCodeLength += tddConfig.decode(is, false);
			subCodeLength += berTag.decode(is);
		}
		
		if (berTag.equals(BerTag.CONTEXT_CLASS, BerTag.PRIMITIVE, 10)) {
			tddSplSfConfig = new BerInteger();
			subCodeLength += tddSplSfConfig.decode(is, false);
			subCodeLength += berTag.decode(is);
		}
		
		if (berTag.equals(BerTag.CONTEXT_CLASS, BerTag.PRIMITIVE, 11)) {
			maxNumConnectedUes = new BerInteger();
			subCodeLength += maxNumConnectedUes.decode(is, false);
			subCodeLength += berTag.decode(is);
		}
		else {
			throw new IOException("Tag does not match the mandatory sequence element tag.");
		}
		
		if (berTag.equals(BerTag.CONTEXT_CLASS, BerTag.PRIMITIVE, 12)) {
			maxNumConnectedBearers = new BerInteger();
			subCodeLength += maxNumConnectedBearers.decode(is, false);
			subCodeLength += berTag.decode(is);
		}
		else {
			throw new IOException("Tag does not match the mandatory sequence element tag.");
		}
		
		if (berTag.equals(BerTag.CONTEXT_CLASS, BerTag.PRIMITIVE, 13)) {
			maxNumUesSchedPerTtiDl = new BerInteger();
			subCodeLength += maxNumUesSchedPerTtiDl.decode(is, false);
			subCodeLength += berTag.decode(is);
		}
		else {
			throw new IOException("Tag does not match the mandatory sequence element tag.");
		}
		
		if (berTag.equals(BerTag.CONTEXT_CLASS, BerTag.PRIMITIVE, 14)) {
			maxNumUesSchedPerTtiUl = new BerInteger();
			subCodeLength += maxNumUesSchedPerTtiUl.decode(is, false);
			subCodeLength += berTag.decode(is);
		}
		else {
			throw new IOException("Tag does not match the mandatory sequence element tag.");
		}
		
		if (berTag.equals(BerTag.CONTEXT_CLASS, BerTag.PRIMITIVE, 15)) {
			dlfsSchedEnable = new BerBoolean();
			subCodeLength += dlfsSchedEnable.decode(is, false);
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
		if (ecgi != null) {
			sb.append("ecgi: ");
			ecgi.appendAsString(sb, indentLevel + 1);
		}
		
		sb.append(",\n");
		for (int i = 0; i < indentLevel + 1; i++) {
			sb.append("\t");
		}
		if (pci != null) {
			sb.append("pci: ").append(pci);
		}
		
		sb.append(",\n");
		for (int i = 0; i < indentLevel + 1; i++) {
			sb.append("\t");
		}
		if (candScells != null) {
			sb.append("candScells: ");
			candScells.appendAsString(sb, indentLevel + 1);
		}
		
		sb.append(",\n");
		for (int i = 0; i < indentLevel + 1; i++) {
			sb.append("\t");
		}
		if (earfcnDl != null) {
			sb.append("earfcnDl: ").append(earfcnDl);
		}
		
		sb.append(",\n");
		for (int i = 0; i < indentLevel + 1; i++) {
			sb.append("\t");
		}
		if (earfcnUl != null) {
			sb.append("earfcnUl: ").append(earfcnUl);
		}
		
		sb.append(",\n");
		for (int i = 0; i < indentLevel + 1; i++) {
			sb.append("\t");
		}
		if (rbsPerTtiDl != null) {
			sb.append("rbsPerTtiDl: ").append(rbsPerTtiDl);
		}
		
		sb.append(",\n");
		for (int i = 0; i < indentLevel + 1; i++) {
			sb.append("\t");
		}
		if (rbsPerTtiUl != null) {
			sb.append("rbsPerTtiUl: ").append(rbsPerTtiUl);
		}
		
		sb.append(",\n");
		for (int i = 0; i < indentLevel + 1; i++) {
			sb.append("\t");
		}
		if (numTxAntenna != null) {
			sb.append("numTxAntenna: ").append(numTxAntenna);
		}
		
		sb.append(",\n");
		for (int i = 0; i < indentLevel + 1; i++) {
			sb.append("\t");
		}
		if (duplexMode != null) {
			sb.append("duplexMode: ").append(duplexMode);
		}
		
		if (tddConfig != null) {
			sb.append(",\n");
			for (int i = 0; i < indentLevel + 1; i++) {
				sb.append("\t");
			}
			sb.append("tddConfig: ").append(tddConfig);
		}
		
		if (tddSplSfConfig != null) {
			sb.append(",\n");
			for (int i = 0; i < indentLevel + 1; i++) {
				sb.append("\t");
			}
			sb.append("tddSplSfConfig: ").append(tddSplSfConfig);
		}
		
		sb.append(",\n");
		for (int i = 0; i < indentLevel + 1; i++) {
			sb.append("\t");
		}
		if (maxNumConnectedUes != null) {
			sb.append("maxNumConnectedUes: ").append(maxNumConnectedUes);
		}
		
		sb.append(",\n");
		for (int i = 0; i < indentLevel + 1; i++) {
			sb.append("\t");
		}
		if (maxNumConnectedBearers != null) {
			sb.append("maxNumConnectedBearers: ").append(maxNumConnectedBearers);
		}
		
		sb.append(",\n");
		for (int i = 0; i < indentLevel + 1; i++) {
			sb.append("\t");
		}
		if (maxNumUesSchedPerTtiDl != null) {
			sb.append("maxNumUesSchedPerTtiDl: ").append(maxNumUesSchedPerTtiDl);
		}

		sb.append(",\n");
		for (int i = 0; i < indentLevel + 1; i++) {
			sb.append("\t");
		}
		if (maxNumUesSchedPerTtiUl != null) {
			sb.append("maxNumUesSchedPerTtiUl: ").append(maxNumUesSchedPerTtiUl);
		}
		
		sb.append(",\n");
		for (int i = 0; i < indentLevel + 1; i++) {
			sb.append("\t");
		}
		if (dlfsSchedEnable != null) {
			sb.append("dlfsSchedEnable: ").append(dlfsSchedEnable);
		}

		sb.append("\n");
		for (int i = 0; i < indentLevel; i++) {
			sb.append("\t");
		}
		sb.append("}");
	}

}

