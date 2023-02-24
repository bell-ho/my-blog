import React, { Children } from 'react';
import styled from '@emotion/styled';
import { Navigation, Pagination, Scrollbar, EffectCoverflow, Mousewheel } from 'swiper';
import { Swiper, SwiperSlide } from 'swiper/react';

import 'swiper/css';
import 'swiper/css/navigation';
import 'swiper/css/pagination';
import 'swiper/css/scrollbar';
import Image from 'next/image';

const ImageBox = ({ imagePaths, onRemoveImage }) => {
  return (
    <Swiper
      effect={'coverflow'}
      grabCursor={true}
      centeredSlides={true}
      slidesPerView={2}
      spaceBetween={10}
      coverflowEffect={{
        rotate: 50,
        stretch: 0,
        depth: 100,
        modifier: 1,
        slideShadows: false,
      }}
      mousewheel={true} // 마우스 휠
      modules={[EffectCoverflow, Pagination, Mousewheel]} // 모듈추가
      className="mySwiper"
    >
      {Children.toArray(
        imagePaths.map((v, i) => (
          <SwiperSlide>
            <SlideWrapper>
              <Image layout={'responsive'} width={300} height={300} src={v} alt={v} />
              <button onClick={onRemoveImage(i)} type={'button'}>
                <span className="material-icons">delete</span>
              </button>
            </SlideWrapper>
          </SwiperSlide>
        )),
      )}
    </Swiper>
  );
};
const SlideWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 10px;
`;
export default ImageBox;
