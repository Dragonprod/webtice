import React, { useState, useEffect } from 'react';
import Radio from '@mui/material/Radio';
import RadioGroup from '@mui/material/RadioGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import FormControl from '@mui/material/FormControl';
import FormLabel from '@mui/material/FormLabel';

import styles from './Question.module.css';

export default function Question(props) {
    const theme = props.theme;
    const questionName = props.questionName;
    const answers = props.answers;



    return (
        <div>
            <h1>{theme}</h1>
            <h1>{questionName}</h1>
            <FormControl component="fieldset">
                <RadioGroup
                    aria-label="gender"
                    defaultValue="female"
                    name="radio-buttons-group"
                >
                    {answers.map((ans) => (
                        <FormControlLabel value={ans.answerName} control={<Radio />} label={ans.answerName} />
                    ))}
                </RadioGroup>
            </FormControl>
        </div>
    );
}