[["java:package:com.mcloud"]]

module cache {

	["java:serializable:java.io.Serializable"]
	sequence<byte> SerializableJavaObj;

	exception CacheException {
    	string msg;
    };

	interface ICacheService {

		SerializableJavaObj get(string key) throws CacheException;

		bool add(string key, SerializableJavaObj value, int expire) throws CacheException;

		bool set(string key, SerializableJavaObj value, int expire) throws CacheException;

        bool evict(string key) throws CacheException;

	};
	
};