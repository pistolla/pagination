package com.eddmash.form.faker.provider;
/*
* This file is part of the Tools package.
* 
* (c) Eddilbert Macharia (http://eddmash.com)<edd.cowan@gmail.com>
*
* For the full copyright and license information, please view the LICENSE
* file that was distributed with this source code.
*/

import com.eddmash.form.faker.Callback;
import com.eddmash.form.faker.FakerException;
import com.eddmash.form.faker.PopulatorInterface;

import org.apache.commons.math3.random.RandomDataGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Provider implements ProviderInterface {
    public static final String SEPARATOR = "\\.";
    public static final String PATTERN = "^([a-z]+)\\.([a-z]+)$";
    protected PopulatorInterface populator;
    public static final String RANDOM_INT = "provider.randomInt";
    protected String format = "#";

    public PopulatorInterface getPopulator() {
        return populator;
    }

    protected String[] mergeArrays(String[] first, String[] second) {
        List<String> both = new ArrayList<String>(first.length + second.length);
        Collections.addAll(both, first);
        Collections.addAll(both, second);
        return both.toArray(new String[both.size()]);
    }

    protected String randomElement(String[] strings) throws FakerException {
        return randomElement(strings, 1);
    }

    protected String randomElement(String[] strings, int count) throws FakerException {
        return randomElements(strings, count)[0];
    }

    protected String[] randomElements(String[] strings) throws FakerException {
        return randomElements(strings, 1);
    }

    protected String[] randomElements(String[] strings, int count) throws FakerException {
        List<String> vals = new ArrayList<>();
        int rangeMax = strings.length;
        if (rangeMax <= 0) {
            throw new FakerException("Can't generate elements with a count of " + count);
        }
        int noElements = 0;
        Double index;
        while (noElements < count) {
            index = Math.random() * (rangeMax - 1);
            vals.add(strings[index.intValue()]);
            noElements++;
        }

        return vals.toArray(new String[vals.size()]);
    }

    protected Integer randomInt() {
        return randomInt(0, 10);
    }

    protected Integer randomInt(int minNumber, int maxNumber) {
        return new RandomDataGenerator().nextInt(minNumber, maxNumber);
    }

    protected Double randomDouble() {
        return randomDouble(0, 10);
    }

    protected Double randomDouble(int minNumber, int maxNumber) {
        return new RandomDataGenerator().nextUniform(minNumber, maxNumber);
    }

    protected String getPersonName(String type) {
        return new Person().setType(type).setPopulator(getPopulator()).generate();
    }

    @Override
    public ProviderInterface setPopulator(PopulatorInterface populator) {
        this.populator = populator;
        return this;
    }

    @Override
    public String toString() {
        return generate();
    }

    @Override
    public String getIdentifier() {
        return getClass().getName();
    }


    public Provider setFormat(String format) {
        this.format = format;
        return this;
    }


    protected String parseFormat(String format, Callback callback) {
        int repeat = format.split("#", -1).length;

        List<Object> args = new ArrayList<>();

        for (int i = 0; i <= repeat - 1; i++) {
            args.add(callback.invoke());
        }
        return String.format(format.replace("#", "%s"), args.toArray());
    }

}