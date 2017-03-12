package com.antiaction.zwave.messages.command;

import java.util.LinkedList;
import java.util.List;

import com.antiaction.zwave.constants.CommandClass;
import com.antiaction.zwave.messages.ApplicationCommandHandlerData;

/**
 * 0x01 0x0F 0x00 0x04 0x00 0x02 0x09 0x86 0x12 0x03 0x04 0x05 0x01 0x07 0x64 0x00 0x0B
 * 0x01 0x0D 0x00 0x04 0x00 0x03 0x07 0x86 0x12 0x06 0x03 0x43 0x01 0x01 0x20
 * 0x01 0x0D 0x00 0x04 0x00 0x04 0x07 0x86 0x12 0x03 0x03 0x34 0x19 0x19 0x55
 */
public class VersionCommand {

	private static final int COMMAND_CLASS = CommandClass.VERSION.getClassCode() & 255;

	public static final int VERSION_GET = 0x11;
	public static final int VERSION_REPORT = 0x12;
	public static final int VERSION_COMMAND_CLASS_GET = 0x13;
	public static final int VERSION_COMMAND_CLASS_REPORT = 0x14;

	protected VersionCommand() {
	}

	private static final byte[] VERSION_GET_REQ;

	static {
		VERSION_GET_REQ = new byte[] {
				(byte)COMMAND_CLASS,
				(byte)VERSION_GET
		};
	}

	public static byte[] assembleVersionGetReq() {
		return VERSION_GET_REQ;
	}

	public static byte[] assembleVersionCommandClassGetReq(byte requestedCommandClass) {
		byte[] data = {
				(byte)COMMAND_CLASS,
				(byte)VERSION_COMMAND_CLASS_GET,
				(byte)requestedCommandClass
		};
		return data;
	}

	public static ApplicationCommandHandlerData disassemble(byte[] data) {
		if (data.length >= 2) {
			int idx = 0;
			int commandClass = data[idx++] & 255;
			int command = data[idx++] & 255;
			if (commandClass == COMMAND_CLASS) {
				switch (command) {
				case VERSION_REPORT:
					if ((data.length - idx) == 5) {
						VersionReportV1 versionReport = new VersionReportV1();
						versionReport.libraryType = data[idx++] & 255;
						versionReport.libraryTypeStr = String.format("%d", versionReport.libraryType);
						versionReport.protocolVersion = new VersionSubVersion(data[idx++] & 255, data[idx++] & 255);
						versionReport.applicationVersion = new VersionSubVersion(data[idx++] & 255, data[idx++] & 255);
						return versionReport;
					}
					else {
						VersionReportV2 versionReport = new VersionReportV2();
						versionReport.libraryType = data[idx++] & 255;
						versionReport.libraryTypeStr = String.format("%d", versionReport.libraryType);
						versionReport.protocolVersion = new VersionSubVersion(data[idx++] & 255, data[idx++] & 255);
						versionReport.firmwareVersions.add(new VersionSubVersion(data[idx++] & 255, data[idx++] & 255));
						versionReport.hardwareVersion = data[idx++] & 255;
						int firmwareTargets = data[idx++] & 255;
						while (firmwareTargets > 0) {
							versionReport.firmwareVersions.add(new VersionSubVersion(data[idx++] & 255, data[idx++] & 255));
							--firmwareTargets;
						}
						return versionReport;
					}
				case VERSION_COMMAND_CLASS_REPORT:
					VersionCommandClassReport versionCommandClassReport = new VersionCommandClassReport();
					versionCommandClassReport.requestedCommandClass = data[idx++] & 255;
					versionCommandClassReport.commandClassVersion = data[idx++] & 255;
					return versionCommandClassReport;
				case VERSION_GET:
				case VERSION_COMMAND_CLASS_GET:
				default:
					break;
				}
			}
		}
		return null;
	}

	public static class VersionSubVersion {
		public int version;
		public int subVersion;
		public String string;
		public VersionSubVersion(int version, int subVersion) {
			this.version = version;
			this.subVersion = subVersion;
			this.string = String.format("%d.%2d", version, subVersion);
		}
	}

	public static class VersionReportV1 extends ApplicationCommandHandlerData {
		public int libraryType;
		public String libraryTypeStr;
		public VersionSubVersion protocolVersion;
		public VersionSubVersion applicationVersion;
	}

	public static class VersionReportV2 extends ApplicationCommandHandlerData {
		public int libraryType;
		public String libraryTypeStr;
		public VersionSubVersion protocolVersion;
		public List<VersionSubVersion> firmwareVersions = new LinkedList<VersionSubVersion>();
		public int hardwareVersion;
	}

	public static class VersionCommandClassReport extends ApplicationCommandHandlerData {
		public int requestedCommandClass;
		public int commandClassVersion;
	}

}
