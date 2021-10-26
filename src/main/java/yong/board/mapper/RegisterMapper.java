package yong.board.mapper;

import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Mapper;
import yong.board.vo.MemberVo;

import java.util.List;

@Repository
@Mapper
public interface RegisterMapper {

    //member 확인
    List<MemberVo> checkMember(MemberVo memberVo);

    //회원 가입
    void joinMember(MemberVo memberVo);

    // 멤버 선택
    MemberVo selectMember(String username);

    //qrcord 불러오기
    String selectQrCord(MemberVo memberVo);

    //sercretkey 불러오기
    String selectSecretKey(MemberVo memberVo);

    //SSO로그인 정보
    void registerSSO(MemberVo memberVo);

    //SSO 확인
    List<MemberVo> checkSSO(MemberVo member);
}
