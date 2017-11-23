package com.eddmash.validation.checks;
/*
* This file is part of the Ziamismalawi package.
* 
* (c) Eddilbert Macharia (http://eddmash.com)<edd.cowan@gmail.com>
*
* For the full copyright and license information, please view the LICENSE
* file that was distributed with this source code.
*/

import android.widget.TextView;

/**
 * Ensures all are validation checks are valid.
 *
 * NOTE:: if the no checks are provided i.e. checkList is empty, validation will always be valid.
 *
 */
public class AllCheck extends CheckCompound {

    public AllCheck(String errorMessage) {
        super(errorMessage);
    }

    @Override
    protected boolean validate() {
        boolean status = true;

        for (ValidationCheck check : checkList) {

            if (!check.run()) {
                status = false;
                break;
            }
        }
        return status;
    }
}
