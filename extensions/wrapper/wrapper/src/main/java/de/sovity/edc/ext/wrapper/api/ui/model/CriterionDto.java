/*
 *  Copyright (c) 2022 sovity GmbH
 *
 *  This program and the accompanying materials are made available under the
 *  terms of the Apache License, Version 2.0 which is available at
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 *  SPDX-License-Identifier: Apache-2.0
 *
 *  Contributors:
 *       sovity GmbH - initial API and implementation
 *
 */

package de.sovity.edc.ext.wrapper.api.ui.model;


import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CriterionDto {
    private String operandLeft;
    private OperatorDto operator;
    private CriterionLiteralDto operandRight;

    public static CriterionDto forString(String operandLeft, OperatorDto operator, CriterionLiteralDto criterionLiteral) {
        return new CriterionDto(operandLeft, operator, criterionLiteral);
    }

    public static CriterionDto forJson(String operandLeft, OperatorDto operator, CriterionLiteralDto operatorRight) {
        return new CriterionDto(operandLeft, operator, operatorRight);
    }
}
