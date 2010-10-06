/*
 * Copyright 2010 Leonard Axelsson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xlson.groovycsv

import au.com.bytecode.opencsv.CSVReader

/**
 * Iterates over the csv data in a non-synchronized way.
 *
 * @author Leonard Axelsson
 * @since 0.1
 */
class CsvIterator implements Iterator {

    private def columns

    private CSVReader csvReader

    private def readValue

    def CsvIterator(def columnNames, CSVReader csvReader) {
        this.columns = [:]
        columnNames.eachWithIndex { name, i ->
            columns."$name" = i
        }

        this.csvReader = csvReader
    }

    boolean hasNext() {
        nextValueIsRead() || (readValue = csvReader.readNext()) != null
    }

    private boolean nextValueIsRead() {
        readValue as boolean
    }

    private def getNextValue() {
        if(nextValueIsRead()) {
            def value = readValue
            readValue = null
            return value
        } else {
            return csvReader.readNext()
        }
    }


    /**
     * Gets the next row in the csv file.
     *
     * @return an instance of <code>PropertyMapper</code>
     */
    def next() {
        new PropertyMapper(columns: columns, values: nextValue)
    }

    /**
     * remove is not supported in CsvIterator.
     *
     * @throws UnsupportedOperationException when called
     */
    void remove() {
        throw new UnsupportedOperationException()
    }
}