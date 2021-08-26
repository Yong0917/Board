package yong.board.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import yong.board.vo.MemberVo;

import java.util.List;

@Repository
@Mapper
public interface MemberMapper {

    //사용자 리스트
    List<MemberVo> selectMemberList();

    //사용자 선택
    MemberVo selectMember(String userId);

    //비밀번호 수정
    void updatePw(MemberVo memberVo);

    //사용자 삭제
    void deleteMember(MemberVo memberVo);

    //게시글 삭제
    void boardDelete(MemberVo memberVo);

    //comment 삭제
    void commentDelete(MemberVo memberVo);
}
