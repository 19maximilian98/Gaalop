/*
 * VectorMethods.h
 *
 *  Created on: 21.11.2011
 *      Author: christian
 */

#ifndef VECTORMETHODS_H_
#define VECTORMETHODS_H_

#include "stdTypes.h"



class VectorMethods {
public:
	VectorMethods();
	virtual ~VectorMethods();

	static bool intVectorEquals(intVector& v1, intVector& v2) {
		if (v1.size() != v2.size()) return false;
		for (unsigned int i=0;i<v1.size();++i)
			if (v1[i] != v2[i]) return false;
		return true;
	}

	static void intListToVector(intList& list, intVector& vec) {
		vec.reserve(list.size());
		vec.clear();

		for (intList::const_iterator ci = list.begin(); ci != list.end(); ++ci)
			vec.push_back(*ci);
	}

	static void intVectorToList(intVector& vec, intList& list) {
		list.clear();

		for (unsigned int i=0;i<vec.size();++i)
			list.push_back(vec[i]);
	}

	static void mergeLists(intList& listDestSrc, intList& listSrc) {
		for (intList::const_iterator ci = listSrc.begin(); ci != listSrc.end(); ++ci)
			listDestSrc.push_back(*ci);
	}

    /**
     * Returns the index of an int in an int list
     * @param search The searched int
     * @param list The list to search in
     * @return The index, -1 if the list doesn't contain the searched int
     */
	static int getIndexOfString(int search, intList& list) {
        int i=0;
        for (intList::const_iterator ci=list.begin();ci != list.end();++ci) {
            if (search == *ci)
                return i;
            i++;
        }
        return -1;
    }



	static void printIntList(intList& list) {
		std::cout << "{";
		int size = list.size();
		int cur = 0;
		for (intList::const_iterator ci = list.begin(); ci != list.end(); ++ci) {
			cur++;
			std::cout << *ci;
			if (cur != size) std::cout << ",";
		}
		std::cout << "}";
	}

	static void printIntVector(intVector& v) {
		std::cout << "{";
		int size = v.size();
		int cur = 0;
		for (intVector::const_iterator ci = v.begin(); ci != v.end(); ++ci) {
			cur++;
			std::cout << *ci;
			if (cur != size) std::cout << ",";
		}
		std::cout << "}";
	}
};

#endif /* VECTORMETHODS_H_ */
