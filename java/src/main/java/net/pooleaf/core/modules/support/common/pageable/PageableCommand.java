package net.pooleaf.core.modules.support.common.pageable;

import lombok.Data;
import net.pooleaf.core.modules.commonsender.common.CommonCommandSender;
import net.pooleaf.core.modules.support.common.CommonChatColor;
import net.pooleaf.core.modules.support.common.component.SimpleComponentBuilder;
import net.pooleaf.core.modules.support.common.messager.Messager;

import java.util.List;

@Data
public abstract class PageableCommand<T> extends CachedPageableList<T> {

    private String entered; // 입력한 명령어


    public PageableCommand(String entered, List<T> values, int countPerPage) {
        super(values, countPerPage);

        this.entered = entered;
    }


    /**
     * 헤더 메시지 색깔을 반환합니다.
     * @return 헤더 메시지 색깔
     */
    public CommonChatColor getHeaderColor() {
        return CommonChatColor.WHITE;
    }

    /**
     * 헤더에 들어갈 메시지를 반환합니다.
     * null일 경우 header을 보내지 않습니다.
     * @return 제일 먼저 보낼 메시지나 BaseComponent
     */
    public String getHeaderMessage() {
        return null;
    }

    /**
     * 페이지 변경 명령어를 반환합니다.
     * @return 페이지 변경 명령어
     */
    public String getPageMoveCommand(int page) {
        return "/" + entered + " " + page;
    }

    /**
     * 값을 메시지나 BaseComponent로 변환하여 반환합니다.
     * @param value 값
     * @return 메시자나 BaseComponent로 변환한 값
     */
    public abstract Object handleValue(T value, int index);

    public void sendPage(CommonCommandSender sender, Integer page) {
        // 페이지 체크
        if (page == null) {
            page = 1;
        }
        if (page < 1 || page > getMaxPage()) {
            Messager.nwarning(sender, "페이지는 1 ~ " + getMaxPage() + "의 정수만 입력할 수 있습니다.");
            return;
        }

        // 헤더
        Messager.nmessage(sender, "");

        String headerMessage = getHeaderColor() + "[ " + getHeaderMessage() + " ]";
        String pageMessage = "( " + page + " / " + getMaxPage() + " )";

        if (getHeaderMessage() != null) {
            if (getMaxPage() <= 1) {
                Messager.nmessage(sender, headerMessage);
            } else {
                if (sender.isConsole()) {
                    Messager.nmessage(sender, headerMessage + " " + pageMessage);
                } else {
                    SimpleComponentBuilder builder = new SimpleComponentBuilder(headerMessage + " " + pageMessage + " ");

                    // 이전 페이지
                    if (page == 1) {
                        builder.addExtra(new SimpleComponentBuilder("§7◀")
                                .hoverShowText("이전 페이지가 없습니다.")
                                .build());
                    } else {
                        builder.addExtra(new SimpleComponentBuilder(getHeaderColor() + "◀")
                                .hoverShowText("클릭 시 이전 페이지로 이동합니다.")
                                .clickRunCommand(getPageMoveCommand(page - 1))
                                .build());
                    }
                    builder.addExtra(" ");

                    // 다음 페이지
                    if (page == getMaxPage()) {
                        builder.addExtra(new SimpleComponentBuilder("§7▶")
                                .hoverShowText("다음 페이지가 없습니다.")
                                .build());
                    } else {
                        builder.addExtra(new SimpleComponentBuilder(getHeaderColor() + "▶")
                                .hoverShowText("클릭 시 다음 페이지로 이동합니다.")
                                .clickRunCommand(getPageMoveCommand(page + 1))
                                .build());
                    }

                    Messager.nmessage(sender, builder.build());
                }
            }
        }

        // 값 메시지
        List<T> pageValues = getPage(page);
        List<Integer> pageIndexes = getPageIndexes(page);
        for (int i = 0; i < pageValues.size(); i++) {
            Messager.nmessage(sender, handleValue(pageValues.get(i), pageIndexes.get(i)));
        }
    }

}
