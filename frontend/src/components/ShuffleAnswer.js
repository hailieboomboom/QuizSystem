import * as React from "react";

export const ShuffleAnswers = (questionCard) => {
    const [question, setQuestion] = React.useState([]);

    const unshuffledAnswers = [
        questionCard.correctAnswer,
        ...questionCard.incorrectAnswers,
    ];

    return unshuffledAnswers
        .map((answer) => ({ sort: Math.random(), value: answer}))
        .sort((a, b) => a.sort - b.sort)
        .map((obj) => obj.value);
};