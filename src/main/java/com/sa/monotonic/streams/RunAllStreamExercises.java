package com.sa.monotonic.streams;

/** Runs every streams/lambdas exercise and prints a grand total. Each ExNN class can also be run on its own. */
public class RunAllStreamExercises {

    public static void main(String[] args) {
        String[] none = new String[0];
        Ex01_LambdaBasics.main(none);
        Ex02_MethodReferences.main(none);
        Ex03_ComposingFunctions.main(none);
        Ex04_StreamBasics.main(none);
        Ex05_LazinessAndShortCircuit.main(none);
        Ex06_ReduceAndMatch.main(none);
        Ex07_Optional.main(none);
        Ex08_FlatMapAndMapMulti.main(none);
        Ex09_CollectorsBasics.main(none);
        Ex10_AdvancedGrouping.main(none);
        Ex11_PrimitiveStreams.main(none);
        Ex12_TakeDropIterateGenerate.main(none);
        Ex13_CustomCollectors.main(none);
        Ex14_FunctionalPatterns.main(none);
        Ex15_ExceptionsInLambdas.main(none);
        Ex16_ParallelStreams.main(none);
        Ex17_SpliteratorsAndCustomSources.main(none);
        Ex18_LogAnalysis.main(none);
        Ex19_BuildYourOwnStream.main(none);
        Ex20_ShopAnalyticsCapstone.main(none);
        Check.grandTotal();
    }
}
