import React, { Children } from 'react';
import styled from '@emotion/styled';
import { Navigation, Pagination, Scrollbar, EffectCoverflow, Mousewheel } from 'swiper';
import { Swiper, SwiperSlide } from 'swiper/react';

import 'swiper/css';
import 'swiper/css/navigation';
import 'swiper/css/pagination';
import 'swiper/css/scrollbar';
import Image from 'next/image';

const ImageSwiper = ({ imagePaths }) => {
  return (
    <Swiper
      effect={'slide'}
      grabCursor={true}
      centeredSlides={true}
      mousewheel={true} // 마우스 휠
      spaceBetween={10}
      modules={[EffectCoverflow, Pagination, Mousewheel]} // 모듈추가
      pagination={true}
      className="mySwiper"
    >
      {Children.toArray(
        imagePaths.map((v, i) => (
          <SwiperSlide>
            <Image layout={'responsive'} width={200} height={200} src={v} alt={v} />
          </SwiperSlide>
        )),
      )}
      <div className="swiper-pagination"></div>
    </Swiper>
  );
};
export default ImageSwiper;
