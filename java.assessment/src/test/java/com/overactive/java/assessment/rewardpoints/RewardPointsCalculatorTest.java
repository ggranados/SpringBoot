package com.overactive.java.assessment.rewardpoints;

import com.overactive.java.assessment.components.RewardPointsOnePointCalculator;
import com.overactive.java.assessment.components.RewardPointsTwoPointsCalculator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RewardPointsCalculatorTest {

    private static RewardPointsOnePointCalculator rewardPoints1PointsCalculator;
    private static RewardPointsTwoPointsCalculator rewardPoints2PointsCalculator;

    @BeforeAll
    private static void setUp(){
        rewardPoints1PointsCalculator = new RewardPointsOnePointCalculator();
        rewardPoints1PointsCalculator.setOnePointBaseAmount("50");
        rewardPoints2PointsCalculator = new RewardPointsTwoPointsCalculator();
        rewardPoints2PointsCalculator.setTwoPointsBaseAmount("100");
    }

    @Test
    @DisplayName("Testing expected 1 point for every dollar spent over $50 in each transaction")
    void calculateBy1PointRule() {
        assertAll(
                ()-> assertEquals(rewardPoints1PointsCalculator.calculate(BigDecimal.ZERO),0L, ()-> "0 points expected to be calculated for amount of 0"),
                ()-> assertEquals(rewardPoints1PointsCalculator.calculate(new BigDecimal(50)),50L,()-> "50 points expected to be calculated for amount of 50"),
                ()-> assertEquals(rewardPoints1PointsCalculator.calculate(new BigDecimal(100)),50L,()-> "50 points expected to be calculated for amount of 100"),
                ()-> assertEquals(rewardPoints1PointsCalculator.calculate(new BigDecimal(120)),50L,()-> "50 points expected to be calculated for amount of 120")
        );
    }

    @Test
    @DisplayName("Testing expected 2 points for every dollar spent over $100 in each transaction")
    void calculateBy2PointRule() {
        assertAll(
                ()-> assertEquals(rewardPoints2PointsCalculator.calculate(BigDecimal.ZERO),0L, ()-> "0 points expected to be calculated for amount of 0"),
                ()-> assertEquals(rewardPoints2PointsCalculator.calculate(new BigDecimal(50)),0L,()-> "0 points expected to be calculated  for amount of 50"),
                ()-> assertEquals(rewardPoints2PointsCalculator.calculate(new BigDecimal(100)),0L, ()-> "0 points expected to be calculated for amount of 100"),
                ()-> assertEquals(rewardPoints2PointsCalculator.calculate(new BigDecimal(120)),40L, ()-> "40 points to be calculated fo amount of 120")
        );
    }

}