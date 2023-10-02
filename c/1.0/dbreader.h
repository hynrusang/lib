/*
To use this header, 
you must use a #define DB structure *
before including this header file.
*/

#if defined DB && !defined DBREADERHEADER

#define DBREADERHEADER
#include "init.h"
typedef struct dbReader DBReader;

struct dbReader {
	FILE* _file;

	// get the length of data.
	int length;
	// gets the index of data from the list according to _Filter function. if data was not exist in list, return -2.
	int (*find)(void* dbReader, void* data, bool (*_Filter)(DB* db, void* resource));
	// return whether read operation completed successfully. read data of pos's data. if pos is -1, read data of last data.
	bool (*read)(void* dbReader, DB* data, int pos);
	// push data to at the last.
	void (*write)(void* dbReader, DB* data);
	// rewrite new data in pos. if pos is -1, rewrite new data at the last. else if pos is negative, push data to at the last.
	void (*rewrite)(void* dbReader, DB* data, int pos);
};

int DBReader$find(DBReader* dbReader, void* data, bool (*filter)(DB* _Structure, void* _Target)) {
	DB* buffer = (DB*)malloc(sizeof(DB));
	if (!buffer) {
		_memory_is_full;
		return -2;
	}
	fseek(dbReader->_file, 0, SEEK_SET);
	for (int pos = 0; fread(buffer, sizeof(DB), 1, dbReader->_file); pos++) if (filter(buffer, data)) return pos;
	return -2;
}
bool DBReader$read(DBReader* dbReader, DB* data, int pos) {
	if (pos < -1) return false;
	fseek(dbReader->_file, (pos != -1) ? sizeof(DB) * pos : -(int)sizeof(DB), (pos != -1) ? SEEK_SET : SEEK_END);
	return fread(data, sizeof(DB), 1, dbReader->_file);
}
void DBReader$write(DBReader* dbReader, DB* data) {
	fseek(dbReader->_file, 0, SEEK_END);
	fwrite(data, sizeof(DB), 1, dbReader->_file);
	dbReader->length++;
}
void DBReader$rewrite(DBReader* dbReader, DB* data, int pos) {
	if (pos < -1 || dbReader->length <= pos) return dbReader->write(dbReader, data);
	fseek(dbReader->_file, (pos != -1) ? sizeof(DB) * pos : -(int)sizeof(DB), (pos != -1) ? SEEK_SET : SEEK_END);
	fwrite(data, sizeof(DB), 1, dbReader->_file);
}

DBReader* newDBReader(char* url) {
	DBReader* temp = _create(DBReader);
	if (temp) {
		temp->_file = fopen(url, "r") ? fopen(url, "rb+") : fopen(url, "wb+");
		temp->length = 0;
		temp->find = DBReader$find;
		temp->read = DBReader$read;
		temp->write = DBReader$write;
		temp->rewrite= DBReader$rewrite;

		DB* buffer = (DB*)malloc(sizeof(DB));
		if (!buffer) {
			_memory_is_full;
			free(temp);
			return NULL;
		}
		while (fread(buffer, sizeof(DB), 1, temp->_file)) temp->length++;
		free(buffer);
	} else _memory_is_full;
	return temp;
}

#endif
