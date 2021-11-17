package yong.board.mapper;

import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Mapper;
import yong.board.vo.MemberVo;
import yong.board.vo.RegisterVO;

import java.util.List;

@Repository
@Mapper
public interface RegisterMapper {

    //member 확인
    List<RegisterVO> checkMember(RegisterVO registerVO);

    //회원 가입
    void joinMember(RegisterVO registerVO);

    // 멤버 선택
    MemberVo selectMember(String username);

    //qrcord 불러오기
    String selectQrCord(MemberVo memberVo);

    //sercretkey 불러오기
    String selectSecretKey(MemberVo memberVo);

    //SSO로그인 정보
    void registerSSO(RegisterVO memberVo);

    //SSO 확인
    List<RegisterVO> checkSSO(RegisterVO member);
}
