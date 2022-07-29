import React from 'react'
import {AppBar, Toolbar, Typography} from '@material-ui/core';
export default function NavBar() {
  return (
    <div>
        <AppBar position='static'>
            <Toolbar>
                <Typography variant='h6' style={style}>
                    React Member Application
                </Typography>
            </Toolbar>
        </AppBar>
    </div>
  )
}

const style = {
    flexGrow: 1
}
