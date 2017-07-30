package com.antiaction.zwave.messages;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import com.antiaction.zwave.FrameUtils;
import com.antiaction.zwave.constants.BasicDeviceClass;
import com.antiaction.zwave.constants.CommandClass;
import com.antiaction.zwave.constants.GenericDeviceClass;
import com.antiaction.zwave.constants.SpecificDeviceClass;

/**
 * TODO
 * Resp: 0x01 0x16 0x00 0x49 0x84 0x03 0x10 0x04 0x08 0x04 0x80 0x46 0x81 0x72 0x8F 0x75 0x43 0x86 0x84 0xEF 0x46 0x81 0x8F 0x16
 * @author nicl
 */
public class ApplicationUpdateResp {

	protected byte[] frame;

	public int status;

	public int nodeId;

	public byte[] payload;

	//public ApplicationUpdateData data;

	public int basicDeviceClassId;

	public BasicDeviceClass basicDeviceClass;

	public int genericDeviceClassId;

	public GenericDeviceClass genericDeviceClass;

	public int optionalSpecificClassId;

	public SpecificDeviceClass optionalSpecificClass;

	public Set<Integer> supportedCommandClassSet = new TreeSet<Integer>();

	public List<CommandClass> supportedCommandClassList = new LinkedList<CommandClass>();;

	public List<Integer> unsupportedCommandClassList = new LinkedList<Integer>();;

	protected ApplicationUpdateResp() {
	}

	public static ApplicationUpdateResp getInstance() {
		return new ApplicationUpdateResp();
	}

	public void disassemble(byte[] frame) {
		this.frame = frame;
		byte[] data = FrameUtils.disassemble(frame);
		int idx = 0;
		status = data[idx++] & 255;
		nodeId = data[idx++] & 255;
		int len = data[idx++] & 255;
		payload = new byte[len];
		System.arraycopy(data, idx, payload, 0, len);
		// Diassemble payload.
		basicDeviceClassId = data[idx++] & 255;
		genericDeviceClassId = data[idx++] & 255;
		optionalSpecificClassId = data[idx++] & 255;
		basicDeviceClass = BasicDeviceClass.getType(basicDeviceClassId);
		genericDeviceClass = GenericDeviceClass.getType(genericDeviceClassId);
		if (genericDeviceClass != null) {
			optionalSpecificClass = genericDeviceClass.getSpecificDeviceClass(optionalSpecificClassId);
		}
		int commandClassId;
		Optional<CommandClass> optionalCommandClass; 
		while (idx < data.length) {
			commandClassId = data[idx++] & 255;
			if (!supportedCommandClassSet.contains(commandClassId)) {
				supportedCommandClassSet.add(commandClassId);
				optionalCommandClass = CommandClass.getType(commandClassId);
				if (optionalCommandClass.isPresent()) {
					supportedCommandClassList.add(optionalCommandClass.get());
				}
				else {
					unsupportedCommandClassList.add(commandClassId);
				}
			}
		}
		Collections.sort(supportedCommandClassList, commandClassComparator);
	}

	public static Comparator<CommandClass> commandClassComparator = new CommandClassComparator();

	public static class CommandClassComparator implements Comparator<CommandClass> {
		@Override
		public int compare(CommandClass o1, CommandClass o2) {
			return o1.getLabel().compareTo(o2.getLabel());
		}
	}

}
