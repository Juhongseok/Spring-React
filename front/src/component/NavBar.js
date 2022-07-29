import React from 'react'
import {AppBar, Toolbar, Typography, Button, IconButton} from '@material-ui/core';
// import MenuIcon from '@material-ui/icons/MenuIcon'
export default function NavBar() {
  return (
    <div>
        <AppBar position='static'>
            <Toolbar>
                <IconButton edge='start' color='inherit' aria-label='Menu'>
                    menu
                </IconButton>
                <Typography variant='h6' style={style}>
                    React Member Application
                </Typography>
                <Button color='inherit'>Login</Button>
            </Toolbar>
        </AppBar>
    </div>
  )
}

const style = {
    flexGrow: 1
}
