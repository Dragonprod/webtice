import React from 'react';
import Autocomplete from '@mui/material/Autocomplete';
import styles from './Search.module.css';
import SearchIcon from '@mui/icons-material/Search';

export default function Search(props) {
  const options = props.options;
  const onChange = props.onChange;
  return (
    <label>
      <Autocomplete
        className={styles.searchbar}
        sx={{
          display: 'inline-block',
          '& input': {
            bgcolor: 'background.paper',
            color: '#2a2a3f',
          },
        }}
        id='custom-input-demo'
        options={options}
        onChange={onChange}
        renderInput={params => (
          <div ref={params.InputProps.ref}>
            <SearchIcon className={styles.searchIcon} />
            <input type='text' {...params.inputProps} />
          </div>
        )}
      />
    </label>
  );
}
