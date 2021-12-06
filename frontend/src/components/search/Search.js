import React, { useState, useEffect } from 'react';
import Autocomplete from '@mui/material/Autocomplete';

export default function Search(props) {
    const options = props;
    return (
        <label>
            <Autocomplete
                sx={{
                    display: 'inline-block',
                    '& input': {
                        width: 200,
                        bgcolor: 'background.paper',
                        color: (theme) =>
                            theme.palette.getContrastText(theme.palette.background.paper),
                    },
                }}
                id="custom-input-demo"
                options={options}
                renderInput={(params) => (
                    <div ref={params.InputProps.ref}>
                        <input type="text" {...params.inputProps} />
                    </div>
                )}
            />
        </label>
    );
}