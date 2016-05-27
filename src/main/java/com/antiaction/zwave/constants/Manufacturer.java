package com.antiaction.zwave.constants;

import java.util.Map;
import java.util.TreeMap;

public class Manufacturer {

	public int id;

	public String name;

    public String website;

	protected static final Object[][] manufacturersArr = new Object[][] {
		{0x0001, "ACT"},
		{0x0086, "Aeon Labs", "http://aeotec.com/"},
		{0x0111, "Airline Mechanical"},
		{0x008A, "BeNext", "http://www.benext.eu/"},
		{0x0138, "BRK Brands"},
		{0x000B, "CasaWorks"},
		{0x000B, "CasaWorks"},
		{0x0116, "Chromagic Technologies", "http://www.ctc-chromagic.com/"},
		{0x000F, "ConvergeX Ltd."},
		{0x001A, "Cooper Wiring Devices"},
		{0x0150, "Cooper Wiring Devices (!!0150!!)"},
		{0x0108, "D-Link", "http://www.dlink.com/"},
		{0x0002, "Danfoss"},
		{0x0175, "Devolo"},
		{0x020E, "Domitech"},
		{0x0184, "Dragon Tech Industrial, Ltd."},
		{0x014A, "Ecolink"},
		{0x011F, "Ecolink(Schlage)"},
		{0x0157, "EcoNet Controls"},
		{0x011A, "Enerwave"},
		{0x0148, "EUROtronic Technology"},
		{0x0060, "Everspring"},
		{0x0135, "Everspring (135)"},
		{0x0113, "Evolve"},
		{0x0085, "Fakro"},
		{0x010F, "Fibaro System", "http://www.fibaro.com/"},
		{0x0084, "FortrezZ"},
		{0x0063, "GE"},
		{0x0099, "GreenWave"},
		{0x001E, "Homeseer"},
		{0x000C, "HomeSeer Technologies", "http://www.homeseer.com/"},
		{0x0039, "Honeywell"},
		{0x0059, "Horstmann Controls"},
		{0x0011, "iCOM Technology"},
		{0x0077, "INNOVUS"},
		{0x0005, "Intermatic"},
		{0x0090, "Kwikset", "http://www.kwikset.com/"},
		{0x001D, "Leviton"},
		{0x014F, "Linear"},
		{0x0071, "LS Control"},
		{0x015F, "MCO Home", "http://www.mcohome.com/"},
		{0x007A, "Merten"},
		{0x008C, "MIOS"},
		{0x000E, "Mohito Networks"},
		{0x007E, "Monster"},
		{0x0178, "Nexia", "http://www.nexiahome.com"},
		{0x0165, "Nodon", "http://www.nodon.fr/"},
		{0x0096, "NorthQ"},
		{0x013C, "Philio Technology Corporation", "http://www.philio-tech.com/"},
		{0x0154, "POPP"},
		{0x0169, "POPP_NEW"},
		{0x0159, "Qubino", "http://www.qubino.com/"},
		{0x0098, "Radio Thermostat Company of America"},
		{0x0064, "Reitz Group"},
		{0x5254, "Remotec"},
		{0x0010, "Residential Control Systems"},
		{0x0147, "RPi"},
		{0x003B, "Schlage"},
		{0x019A, "Sensative"},
		{0x0000, "Sigma Designs"},
		{0x0239, "Stelpro", "http://www.stelpro.com/"},
		{0x0166, "Swiid"},
		{0x000A, "Techniku"},
		{0x0176, "Telldus", "http://www.telldus.com/"},
		{0x019B, "Thermo-Floor"},
		{0x008B, "Trane"},
		{0x0214, "Unknown (0x0214)"},
		{0x0007, "Vimar CRS"},
		{0x0109, "Vision Security", "http://www.visionsecurity.com.tw/"},
		{0x0118, "Wenzhou TKB Control System"},
		{0x0149, "WiDOM"},
		{0x0097, "Wintop"},
		{0x0003, "Wrap"},
		{0x0129, "Yale Locks"},
		{0x0131, "Zipato", "http://www.zipato.com/"},
		{0x0115, "ZWave.me"}
	};

	public static final Map<Integer, Manufacturer> manufacturerMap = new TreeMap<>();

	static {
		Manufacturer manufacturer;
		for (int i=0; i<manufacturersArr.length; ++i) {
			manufacturer = new Manufacturer();
			manufacturer.id = (int)manufacturersArr[i][0];
			manufacturer.name = (String)manufacturersArr[i][1];
			if (manufacturersArr[i].length > 2) {
				manufacturer.website = (String)manufacturersArr[i][2];
			}
			manufacturerMap.put(manufacturer.id, manufacturer);
		}
	}

}
