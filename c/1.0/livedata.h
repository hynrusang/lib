/*
To use this header, 
you must use a #define livetype *
before including this header file.
*/

#if defined livetype && !defined LIVEDATAHEADER

#define LIVEDATAHEADER
#include "init.h"
typedef struct livedata LiveData;

struct livedata {
	livetype _Value;
	void (*_Observer)(LiveData* livedata);

	// return whether data was changed. set data of livedata. if data was changed, call _Observer function.
	bool (*set)(LiveData* livedata, livetype newData);
	// get data of livedata.
	livetype (*get)(LiveData* livedata);
};

bool LiveData$set(LiveData* livedata, livetype newData) {
	if (newData == livedata->_Value) return false;
	livedata->_Value = newData;
	livedata->_Observer(livedata);
	return true;
}
livetype LiveData$get(LiveData* livedata) {
	return livedata->_Value;
}
LiveData* newLiveData(livetype initdata, void (*_Observer)(LiveData* livedata)) {
	LiveData* temp = _create(LiveData);
	if (temp) {
		temp->_Value = initdata;
		temp->_Observer = _Observer;
		temp->set = LiveData$set;
		temp->get = LiveData$get;
	} else _memory_is_full;
	return temp;
}

#endif