import React from 'react';
import classes from './hero.module.css';
import Image from 'next/image';
const Hero = () => {
  return (
    <section className={classes.hero}>
      <div className={classes.image}>
        <Image width={300} height={300} src={'/images/site/dog.jpg'} alt={'good'} />
      </div>
      <h1>hi good</h1>
      <p>good</p>
    </section>
  );
};

export default Hero;
