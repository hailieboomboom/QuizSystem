import * as React from "react";

export const ShuffleTakeQuizAnswers = (options) => {

    return options
        .map((answer) => ({ sort: Math.random(), value: answer}))
        .sort((a, b) => a.sort - b.sort)
        .map((obj) => obj.value);
};
