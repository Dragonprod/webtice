import React, { useState } from 'react';
import Radio from '@mui/material/Radio';
import RadioGroup from '@mui/material/RadioGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import FormControl from '@mui/material/FormControl';
import Button from '@mui/material/Button';

import styles from './Question.module.css';

export default function Question(props) {
  const onClick = props.onClick;
  const theme = props.theme;
  const questionId = props.questionId;
  const questionName = props.questionName;
  const answers = props.answers;

  const [currentAnswer, setCurrentAnswer] = useState('')

  const handleAnswerChange = e => {
    setCurrentAnswer(e.target.value)
  }

  return (
    <div className={styles.questionCard}>
      <h2 className={styles.testThemeTitle}>{theme}</h2>
      <h3 className={styles.questionName}>{questionId}. {questionName}</h3>
      <FormControl component='fieldset'>
        <RadioGroup
          onChange={handleAnswerChange}
          aria-label='answers-group'
          name='radio-buttons-group'>
          {answers.map(ans => (
            <FormControlLabel
              className={styles.muiRadioButton}
              value={ans.answerName}
              control={
                <Radio
                  sx={{
                    color: '#fb39f9',
                    '&.Mui-checked': {
                      color: '#fb39f9',
                    },
                  }}
                />
              }
              label={ans.answerName}
            />
          ))}
        </RadioGroup>
        <Button
          className={styles.muiContinueButton}
          variant='contained'
          onClick={() => onClick(currentAnswer)}>
          Далее
        </Button>
      </FormControl>
    </div>
  );
}
