[["java:package:com.yonyou.mcloud"]]

module idgenerator {
	interface IdGenerator {
		string nextIdByModule(string moduleCode);
		string nextId();
	};
};